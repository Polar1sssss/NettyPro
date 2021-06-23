package com.hujtb.netty.inoutboundhandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.concurrent.EventExecutorGroup;

import java.util.List;

/**
 * ByteToMessageDecoder的父类是ChannelInboundHandlerAdapter
 */
public class MyByteToLongDecoder extends ByteToMessageDecoder {
    /**
     * 该方法会根据数据的多少，被调用多次，直到确定没有新元素被添加到list
     * 如果list不为空，就会将list的内容传递给下一个handler，该handler中方法也会被调用多次
     * @param ctx 上下文
     * @param in 入站的Bytebuf
     * @param out List集合，将解码后的数据传递给下一个handler
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("MyByteToLongDecoder 被调用");
        // 判断有8个字节才能读取一个long类型
        if (in.readableBytes() >= 8) {
            out.add(in.readLong());
        }
    }
}
