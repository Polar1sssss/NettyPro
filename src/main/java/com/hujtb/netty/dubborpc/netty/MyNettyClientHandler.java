package com.hujtb.netty.dubborpc.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.Callable;

/**
 * @author hujtb
 */
public class MyNettyClientHandler extends ChannelInboundHandlerAdapter implements Callable {

    private ChannelHandlerContext context;
    private String result;
    private String param;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 在其他方法会用到ctx
        context = ctx;
    }

    @Override
    public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 服务器端返回的结果
        result = msg.toString();
        // 接收到服务器数据后，唤醒等待线程
        notify();
    }

    /**
     * 被代理对象调用，发送数据给服务器，等待被channelRead唤醒
     * @return
     * @throws Exception
     */
    @Override
    public synchronized Object call() throws Exception {
        // 向服务端发送消息
        context.writeAndFlush(param);
        wait();
        return result;
    }

    /**
     * 设置参数
     * @param param
     */
    public void setParam(String param) {
        this.param = param;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
