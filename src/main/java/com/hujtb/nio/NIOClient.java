package com.hujtb.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * NIO非阻塞网络编程模型（客户端）
 * @Author: hujtb
 * @Date: 2020/11/12 11:37
 */
public class NIOClient {
    public static void main(String[] args) throws IOException {
        // 建立网络通道
        SocketChannel sc = SocketChannel.open();
        // 设置通道非阻塞模式
        sc.configureBlocking(false);
        // 提供服务器端地址
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 6666);
        // 连接服务器
        if (!sc.connect(inetSocketAddress)) {
            while (!sc.finishConnect()) {
                System.out.println("因为连接需要时间，客户端不会阻塞，可以做其他工作。。。");
            }
        }

        String s = "hello, hujtb";
        ByteBuffer buffer = ByteBuffer.wrap(s.getBytes());
        // 向SocketChannel中写入buffer中的数据
        sc.write(buffer);
        System.in.read();
    }
}
