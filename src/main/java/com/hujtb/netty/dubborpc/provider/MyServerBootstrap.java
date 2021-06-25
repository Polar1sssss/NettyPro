package com.hujtb.netty.dubborpc.provider;

import com.hujtb.netty.dubborpc.netty.NettyServer;

/**
 * 服务提供者启动类，就是NettyServer
 */
public class MyServerBootstrap {
    public static void main(String[] args) {
        NettyServer.startServer("127.0.0.1", 7000);
        System.out.println("服务提供方开始提供服务...");
    }
}
