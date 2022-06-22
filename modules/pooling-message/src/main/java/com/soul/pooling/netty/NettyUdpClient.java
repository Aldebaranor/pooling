package com.soul.pooling.netty;

import com.soul.pooling.model.MessageProtocol;
import com.soul.pooling.netty.handler.NettyUdpClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.CharsetUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.net.InetSocketAddress;

/**
 * @Description:
 * @Author: nemo
 * @Date: 2022/3/23
 */
@Data
@Component
@Configuration
@Slf4j
@ConfigurationProperties(prefix = "netty")
@ConditionalOnProperty(prefix = "netty.udp.client", name = "enable", havingValue = "true", matchIfMissing = false)
public class NettyUdpClient {

    private Bootstrap bootstrap = new Bootstrap();

    private NioEventLoopGroup group = new NioEventLoopGroup();

    private Channel channel;

    private Integer clientPort;

    private Integer serverPort;

    private String serverIp;

    @PostConstruct
    public void start() throws InterruptedException {
        bootstrap.group(group)
                .channel(NioDatagramChannel.class)
                .option(ChannelOption.SO_BROADCAST, true)
                .option(ChannelOption.SO_RCVBUF, 1024 * 1024 * 100)
                .option(ChannelOption.SO_SNDBUF, 1024 * 1024 * 100)
                .option(ChannelOption.RCVBUF_ALLOCATOR, new FixedRecvByteBufAllocator(65535))
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel channel) throws Exception {
                        ChannelPipeline pipeline = channel.pipeline();
                        pipeline.addLast(new NettyUdpClientHandler());
                    }
                });
        channel = bootstrap.bind(clientPort).sync().channel();
        log.info("----------------------------UdpClient start success");
    }

    @PreDestroy
    public void destory() throws InterruptedException {
        group.shutdownGracefully().sync();
        log.info("----------------------------关闭Netty");
    }

    public void sendToServer(String msg) {
        try {
            MessageProtocol protocol = new MessageProtocol(msg.getBytes(CharsetUtil.UTF_8).length,msg.getBytes(CharsetUtil.UTF_8));
            this.channel.writeAndFlush(new DatagramPacket(protocol.toByteBuf(), new InetSocketAddress(serverIp, serverPort)));
        } catch (Exception e) {
            return;
        }
    }


}