package com.hujtb.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @Author: hujtb
 * @Date: 2020/11/15 11:48
 */
public class NettyServer {
    public static void main(String[] args) throws InterruptedException {
        // 创建两个线程组bossGroup和workerGroup
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            // 创建服务器端的启动对象，配置参数
            ServerBootstrap sbs = new ServerBootstrap();

            sbs.group(bossGroup, workerGroup) // 设置两个线程组
                    .channel(NioServerSocketChannel.class) // 使用NioServerSocketChannel作为服务器的通道实现
                    .option(ChannelOption.SO_BACKLOG, 128) // 设置线程队列得到链接个数
                    .childOption(ChannelOption.SO_KEEPALIVE, true) // 设置保持活动链接状态
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        // 给pipeline设置处理器
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new NettyServerHandler());
                        }
                    });
            System.out.println("服务端准备就绪...");
            ChannelFuture sync = sbs.bind(6668).sync();
            sync.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
