package com.hujtb.netty.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

public class MyServer {
    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup(); //8个NioEventLoop

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            // 基于http协议，使用http的编解码器
                            pipeline.addLast(new HttpServerCodec());
                            // 以块方式写，添加ChunkedWriter处理器
                            pipeline.addLast(new ChunkedWriteHandler());
                            // http数据在传输过程中分段，HttpObjectAggregator可以将多个段聚合，
                            // 这就是为什么浏览器发送大量数据时，就会发出多段http请求
                            pipeline.addLast(new HttpObjectAggregator(8192));
                            // 对应websocket，数据以帧（frame）形式进行传递，WebSocketFrame下面有六个子类
                            // 浏览器请求时 ws://localhost:7000/xxx
                            // WebSocketServerProtocolHandler核心功能是将http协议升级为ws协议，保持长连接
                            // 通过状态码101切换成ws协议
                            pipeline.addLast(new WebSocketServerProtocolHandler("/hello"));
                            pipeline.addLast(new MyTextWebSocketFrameHandler());
                        }
                    });
            ChannelFuture future = serverBootstrap.bind(7000).sync();
            future.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
