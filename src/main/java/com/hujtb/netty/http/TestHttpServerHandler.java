package com.hujtb.netty.http;

import com.sun.jndi.toolkit.url.Uri;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

/**
 * SimpleChannelInboundHandler是ChannelInboundHandlerAdapter的一个子类
 * HttpObject：客户端和服务端相互通讯的数据被封装成HttpObject
 */
public class TestHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {

        // 如果是HttpRequest请求
        if (msg instanceof HttpRequest) {
            System.out.println("pipeline hashcode：" + ctx.pipeline().hashCode());
            System.out.println("msg类型：" + msg.getClass());
            System.out.println("客户端地址：" + ctx.channel().remoteAddress());

            //获取HTTP请求，对特定资源不做响应
            HttpRequest httpRequest = (HttpRequest) msg;
            Uri uri = new Uri(httpRequest.uri());
            if ("/favicon.ico".equals(uri.getPath())) {
                System.out.println("图标资源不做响应");
                return;
            }

            ByteBuf content = Unpooled.copiedBuffer("hello, im server01", CharsetUtil.UTF_8);

            // 构造一个HTTP响应，即httpresponse
            DefaultFullHttpResponse httpResponse =
                    new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
            httpResponse.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
            httpResponse.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());
            ctx.writeAndFlush(httpResponse);
        }
    }
}
