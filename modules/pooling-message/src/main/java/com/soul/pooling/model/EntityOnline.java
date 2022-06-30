package com.soul.pooling.model;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 王质松
 * @date 2022/6/30 9:29
 */
@Data
public class EntityOnline implements Serializable {

    public static final int HEAD_DATA = 0XFFA0;
    /**
     * 消息的开头的信息标志
     */
    private int headData = HEAD_DATA;
    /**
     * 消息的长度
     */
    private int contentLength;

    /**
     * 实体ID
     */
    private int entityId;


    public EntityOnline(int contentLength,int entityId) {
        this.contentLength = contentLength;
        this.entityId = entityId;
    }


    public byte[] toByteArray() {
        ByteBuf buf = Unpooled.buffer(32);
        buf.writeInt(headData);
        buf.writeInt(getContentLength());
        buf.writeInt(getEntityId());
        return buf.array();
    }

    public ByteBuf toByteBuf() {
        ByteBuf buf = Unpooled.buffer(32);
        buf.writeInt(headData);
        buf.writeInt(getContentLength());
        buf.writeInt(getEntityId());
        return buf;
    }




}
