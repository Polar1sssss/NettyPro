package com.hujtb.netty.protocoltcp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;

public class MyClientHandler extends SimpleChannelInboundHandler<MessageProtocol> {
    private int count;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
        int length = msg.getLength();
        String content = new String(msg.getContent(), Charset.forName("utf-8"));
        System.out.println("客户端接到消息：长度 -- " + length + ", 内容 -- " + content);
        System.out.println("这是客户端接收到的第" + (++this.count) + "条消息");
        System.out.println("==========================");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 5; i++) {
            String message = "今天天气冷，吃火锅";
            byte[] content = message.getBytes(Charset.forName("utf-8"));
            int length = content.length;
            MessageProtocol mp = new MessageProtocol(length, content);
            ctx.writeAndFlush(mp);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
