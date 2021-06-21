package com.hujtb.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class NettyBuf01 {
    public static void main(String[] args) {
        // 创建一个ByteBuf对象，对象包含一个数组（byte[10]）
        // Netty的buffer不需要使用flip反转
        // 原因是底层通过writerIndex、readerIndex和capcity将buffer分成三个区域：
        // 0 -> readerIndex：已经读取区域
        // readerIndex -> writerIndex：可读区域
        // writerIndex -> capcity：可写区域
        ByteBuf buffer = Unpooled.buffer(10);
        for (int i = 0; i < 10; i++) {
            buffer.writeByte(i);
        }
        System.out.println("capcity=" + buffer.capacity());
        for (int i = 0; i < buffer.capacity(); i++) {
            System.out.println(buffer.getByte(i));
            //System.out.println(buffer.readByte());
        }

    }
}
