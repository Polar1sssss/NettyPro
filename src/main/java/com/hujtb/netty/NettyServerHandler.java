package com.hujtb.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * 自定义一个Handler，需要继承netty规定好的某个handlerAdapter，这样才是一个真正的handler
 * @Author: hujtb
 * @Date: 2020/11/15 11:49
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    /**
     *
     * @param ctx：上下文对象，含有管道pipeline、通道channel
     * @param msg：客户端发送的数据
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("服务器读取线程：" + Thread.currentThread().getName());
        System.out.println("server ctx = " + ctx);
        // 将msg转成一个ByteBuf
        ByteBuf buffer = (ByteBuf) msg;
        System.out.println("客户端发送消息是：" + buffer.toString(CharsetUtil.UTF_8));
        System.out.println("客户端地址是：" + ctx.channel().remoteAddress());
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        // 将数据写入到缓存，并刷新
        // 一般来讲，我们对发送数据进行编码
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello, 客户端, miao", CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
