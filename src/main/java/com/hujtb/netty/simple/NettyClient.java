package com.hujtb.netty.simple;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @Author: hujtb
 * @Date: 2020/11/16 17:34
 */
public class NettyClient {
    public static void main(String[] args) throws InterruptedException {
        // 客户端需要一个事件循环组
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            // 创建客户端启动对象，使用的是BootStrap
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class) // 设置客户端通道的实现类（反射）
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception {
                            channel.pipeline().addLast(new NettyClientHandler());
                        }
                    });
            System.out.println("客户端 ok...");
            // 启动客户端去连接服务器端，关于ChannelFuture要分析，涉及到netty的异步模型
            ChannelFuture sync = bootstrap.connect("127.0.0.1", 6668).sync();
            // 对关闭通道进行监听
            sync.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }
}
