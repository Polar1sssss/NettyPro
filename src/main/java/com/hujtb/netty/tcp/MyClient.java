package com.hujtb.netty.tcp;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * 粘包拆包演示
 */
public class MyClient {
    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new MyClientInitializer());

            // 绑定到7000端口
            ChannelFuture future = bootstrap.connect("127.0.0.1", 7000).sync();
            // closeFuture，closeFuture，closeFuture
            future.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }
}
