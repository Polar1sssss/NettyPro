package com.hujtb.netty.protocoltcp;

import com.google.protobuf.Message;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class MyServerHandler extends SimpleChannelInboundHandler<MessageProtocol> {
    private int count;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
       int length = msg.getLength();
       byte[] content = msg.getContent();
        System.out.println("服务端接收到信息如下：");
        System.out.println("长度：" + length);
        System.out.println("内容：" + new String(content, Charset.forName("utf-8")));
        System.out.println("这是服务端接收到的第" + (++this.count) + "条消息");
        System.out.println("==========================");

        // 回复消息
        String responseText = UUID.randomUUID().toString();
        byte[] content1 = responseText.getBytes("utf-8");
        int length1 = content1.length;
        MessageProtocol messageProtocol = new MessageProtocol(length1, content1);
        ctx.writeAndFlush(messageProtocol);
    }
}
