package com.hujtb.netty.simple;

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
        // 含有子线程（事件循环）的个数默认是cpu核数 * 2
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            // 创建服务器端的启动对象，配置参数
            ServerBootstrap sbs = new ServerBootstrap();

            sbs.group(bossGroup, workerGroup) // 设置两个线程组
                    .channel(NioServerSocketChannel.class) // 使用NioServerSocketChannel作为服务器的通道实现
                    .option(ChannelOption.SO_BACKLOG, 128) // 设置线程队列等待连接个数
                    .childOption(ChannelOption.SO_KEEPALIVE, true) // 设置保持活动连接状态
                    .handler(null) // 该handler与bossGroup对应，childHandler与workerGroup对应
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        // 给pipeline设置处理器
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new NettyServerHandler());
                        }
                    });
            System.out.println("服务端准备就绪...");
            // 绑定一个端口并同步
            ChannelFuture cf = sbs.bind(6668).sync();
            // 给cf注册监听器，监听关心的事件
            cf.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (cf.isSuccess()) {
                        System.out.println("监听6688端口成功");
                    } else {
                        System.out.println("监听6688端口失败");
                    }
                }
            });
            // 对关闭通道进行监听
            cf.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
