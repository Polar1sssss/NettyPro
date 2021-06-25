package com.hujtb.netty.dubborpc.publicinterface;

/**
 * 消费者生产者都需要的类
 */
public interface HelloService {
    String hello(String message);
}
