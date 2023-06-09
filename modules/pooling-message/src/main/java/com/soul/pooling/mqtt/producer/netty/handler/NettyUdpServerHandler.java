package com.soul.pooling.netty.handler;

import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.jboss.netty.util.internal.ByteBufferUtil;

/**
 * @Description: udp服务端
 * @Author: nemo
 * @Date: 2022/6/22
 */
@Slf4j
public class NettyUdpServerHandler extends SimpleChannelInboundHandler<DatagramPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, DatagramPacket datagramPacket) throws Exception {
//        String host = "127.0.0.1";
//        if (datagramPacket.sender().getAddress().getHostAddress().contains(host)) {
//            return;
//        }
        String req = datagramPacket.content().toString(CharsetUtil.UTF_8);
        String s = ByteBufUtil.prettyHexDump(datagramPacket.content());
        System.out.println("收到数据：" + req);
        System.out.println(s);


        channelHandlerContext.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer(
                req, CharsetUtil.UTF_8), datagramPacket.sender()));
    }

}