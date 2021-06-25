package com.hujtb.netty.dubborpc.netty;

import com.hujtb.netty.dubborpc.publicinterface.HelloServiceImpl;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author hujtb
 */
public class MyNettyServerHandler extends SimpleChannelInboundHandler<String> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println("msg=" + msg);
        // 客户端调用服务端api时，需要定义一个协议
        // 比如每次发送消息都必须以某个字符串开头（HelloService#hello#）
        if (msg.startsWith("HelloService#hello#")) {
            String result = new HelloServiceImpl().hello(msg.substring(msg.lastIndexOf("#") + 1));
            ctx.writeAndFlush(result);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
