package com.soul.pooling.netty;

import com.soul.pooling.netty.handler.NettyUdpServerHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.CharsetUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.net.InetSocketAddress;

 /**
 * @Description: udp服务端
 * @Author: nemo
 * @Date: 2022/6/22
 */
@Data
@Slf4j
@Component
@ConfigurationProperties(prefix = "netty")
@ConditionalOnProperty(prefix = "netty.udp.server", name = "enable", havingValue = "true", matchIfMissing = false)
public class NettyUdpServer {

    private Bootstrap bootstrap = new Bootstrap();

    private NioEventLoopGroup group = new NioEventLoopGroup();

    private Channel channel;

     private Integer clientPort;

     private Integer serverPort;


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
                        pipeline.addLast(new NettyUdpServerHandler());
                    }
                });
        channel = bootstrap.bind(serverPort).sync().channel();
        log.info("----------------------------UdpServer start success");
    }

    @PreDestroy
    public void destory() throws InterruptedException {
        group.shutdownGracefully().sync();
        log.info("----------------------------关闭NettyServer");
    }

    public void sendToAll(String msg) {
        try {
            this.channel.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer(
                    msg, CharsetUtil.UTF_8), new InetSocketAddress("255.255.255.255", clientPort)));
        } catch (Exception e) {
            return;
        }
    }


}