package com.hujtb.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * NIO非阻塞网络编程模型（服务端）
 * @Author: hujtb
 * @Date: 2020/11/12 10:59
 */
public class NIOServer {
    public static void main(String[] args) throws IOException {
        // 生成ServerSocketChannel对象
        ServerSocketChannel ssc = ServerSocketChannel.open();
        // 得到一个Selector对象
        Selector selector = Selector.open();

        // 绑定端口，在服务器端监听
        ssc.socket().bind(new InetSocketAddress(6666));
        // 设置为非阻塞
        ssc.configureBlocking(false);
        // 把ServerSocketChannel注册到Selector, 关心的事件是OP_ACCEPT
        ssc.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            if (selector.select(1000) == 0) {
                System.out.println("服务器等待了1s，没有事件发生");
                continue;
            }

            // 如果select()方法返回值不为零，证明Selector监听到了有通道发生事件
            // 获取到关注事件的SelectionKey集合
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> keyIter = selectionKeys.iterator();

            while (keyIter.hasNext()) {
                SelectionKey key = keyIter.next();
                // 根据key对应通道发生的事件做相应处理，客户端连接事件
                if (key.isAcceptable()) {
                    SocketChannel sc = ssc.accept();
                    // channel指定为非阻塞模式才能注册进Selector
                    sc.configureBlocking(false);
                    System.out.println("客户端连接成功，生成一个SocketChannel");
                    sc.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                }
                if (key.isReadable()) {
                    // 如果Selector监听到了读事件，通过SelectionKey反向获取通道
                    SocketChannel channel = (SocketChannel) key.channel();
                    ByteBuffer buffer = (ByteBuffer) key.attachment();
                    // 从通道向buffer读数据
                    channel.read(buffer);
                    System.out.println("from客户端，position：" + buffer.position() + "--" + new String(buffer.array()));
                }
                // 手动从当前集合删除SelctionKey，防止重复操作
                keyIter.remove();
            }
        }
    }
}
