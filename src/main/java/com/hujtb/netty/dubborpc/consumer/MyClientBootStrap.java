package com.hujtb.netty.dubborpc.consumer;

import com.hujtb.netty.dubborpc.netty.NettyClient;
import com.hujtb.netty.dubborpc.publicinterface.HelloService;

public class MyClientBootStrap {
    private static final String providerName = "HelloService#hello#";

    public static void main(String[] args) {
        NettyClient consumer = new NettyClient();
        HelloService service = (HelloService) consumer.getBean(HelloService.class, providerName);
        String res = service.hello("dubbo rpc");
        System.out.println("服务端调用结果返回：" + res);
    }
}
