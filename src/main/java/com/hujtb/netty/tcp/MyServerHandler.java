package com.hujtb.netty.tcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;
import java.util.UUID;

public class MyServerHandler extends SimpleChannelInboundHandler<ByteBuf> {
    private int count;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        byte[] buffer = new byte[msg.readableBytes()];
        msg.readBytes(buffer);
        // 将buffer转为字符串
        String message = new String(buffer, Charset.forName("utf-8")) + " ";
        System.out.println("从客户端" + ctx.channel().remoteAddress() + "接收的消息为：" + message);
        System.out.println("服务器端接收到的消息量=" + (++this.count));

        // 服务器回送数据给客户端
        ByteBuf responoseByteBuf = Unpooled.copiedBuffer(UUID.randomUUID().toString(), Charset.forName("utf-8"));
        ctx.writeAndFlush(responoseByteBuf);
    }
}
