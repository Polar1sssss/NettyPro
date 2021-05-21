package com.hujtb.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

/**
 * 自定义一个Handler，需要继承netty规定好的某个HandlerAdapter（规范），才能有相关的功能
 * @Author: hujtb
 * @Date: 2020/11/15 11:49
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    /**
     * 通道读取数据触发事件
     * @param ctx：上下文对象，含有管道pipeline、通道channel
     * @param msg：客户端发送的数据
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 耗时任务 -> 异步执行：提交任务到该Channel对应的NioEventLoop的taskQueue中
        // 解决方案1：用户自定义普通任务
        ctx.channel().eventLoop().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10 * 1000);
                    ctx.writeAndFlush(Unpooled.copiedBuffer("hello, 客户端, miao2", CharsetUtil.UTF_8));
                } catch (Exception e) {
                    System.out.println("发生异常：" + e.getMessage());
                }
            }
        });

        // 解决方案2：自定义定时任务，该任务提交到scheduledTaskQueue
        ctx.channel().eventLoop().schedule(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10 * 1000);
                    ctx.writeAndFlush(Unpooled.copiedBuffer("hello, 客户端, miao3", CharsetUtil.UTF_8));
                } catch (Exception e) {
                    System.out.println("发生异常：" + e.getMessage());
                }
            }
        }, 5L, TimeUnit.SECONDS);

        // 解决方案3：非当前Reactor线程调用Channel的各种方法

        System.out.println("go on");
//        System.out.println("服务器读取线程：" + Thread.currentThread().getName());
//        System.out.println("server ctx = " + ctx);
//        System.out.println("Channel和pipeline的关系");
//        Channel channel = ctx.channel();
//        ChannelPipeline pipeline = ctx.pipeline(); // 本质是一个双向链表，出栈入栈
//
//        // 将msg转成一个ByteBuf
//        ByteBuf buffer = (ByteBuf) msg;
//        System.out.println("客户端发送消息是：" + buffer.toString(CharsetUtil.UTF_8));
//        System.out.println("客户端地址是：" + channel.remoteAddress());
    }

    /**
     * 数据读取完毕触发事件
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        // 将数据写入到缓存，并刷新
        // 对发送数据进行编码
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello, 客户端, miao1", CharsetUtil.UTF_8));
    }

    /**
     * 通道发生异常触发事件
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
