package com.soul.pooling.utils;

import io.netty.buffer.ByteBuf;

 /**
 * @Description:
 * @Author: nemo
 * @Date: 2022/6/22
 */
public class NettyUtils {

    public static String convertByteBufToString(ByteBuf byteBuf) {
        String msg;
        if (byteBuf.hasArray()) {
            msg = new String(byteBuf.array(), byteBuf.arrayOffset() + byteBuf.readerIndex(), byteBuf.readableBytes());
        } else {
            byte[] bytes = new byte[byteBuf.readableBytes()];
            byteBuf.getBytes(byteBuf.readerIndex(), bytes);
            msg = new String(bytes, 0, byteBuf.readableBytes());
        }

        return msg;

    }


}

