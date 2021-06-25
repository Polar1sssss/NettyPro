package com.hujtb.netty.dubborpc.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.lang.reflect.Proxy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NettyClient {

    private static ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private static MyNettyClientHandler clientHandler;

    /**
     * 创建客户端代理对象
     * @param servicClass
     * @param providerName 协议头
     * @return
     */
    public Object getBean(final Class<?> servicClass, final String providerName) {
        Object o = Proxy.newProxyInstance(
                Thread.currentThread().getContextClassLoader(),
                new Class<?>[] {servicClass},
                (proxy, method, args) -> {
                    if (clientHandler == null) {
                        initClient();
                    }
                    clientHandler.setParam(providerName + args[0]);
                    // 将ClientHandler提交到线程池执行，并返回结果
                    return pool.submit(clientHandler).get();
                });
        return o;
    }

    /**
     * 初始化客户端
     */
    private static void initClient() {
        clientHandler = new MyNettyClientHandler();
        NioEventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bs = new Bootstrap();
        try {
            bs.group(group).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new StringEncoder());
                            pipeline.addLast(new StringDecoder());
                            pipeline.addLast(clientHandler);
                        }
                    });
            bs.connect("127.0.0.1", 7000).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
