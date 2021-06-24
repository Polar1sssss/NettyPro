package com.hujtb.netty.protocoltcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

public class MyMessageDecoder extends ReplayingDecoder<Void> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("MyMessageDecoder decode 被调用");

        // 将二进制字节码转成MessageProtocol对象
        int i = in.readInt();
        byte[] bytes = new byte[i];
        in.readBytes(bytes);

        // 封装成MessageProtocol对象
        MessageProtocol mp = new MessageProtocol(i, bytes);
        out.add(mp);
    }
}
