package com.hujtb.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * Scattering：将数据写入buffer时，可以采用buffer数组依次写入
 * Gathering：将数据从buffer读出时，依次从buffer数组读出
 * @Author: hujtb
 * @Date: 2020/11/11 16:24
 */
public class ScatteringAndGatheringDemo {
    /**
     * 使用SocketChannel和ServerSocketChannel
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress address = new InetSocketAddress(7000);
        // 绑定端口到ServerSocket并启动
        serverSocketChannel.socket().bind(address);

        // 服务端创建buffer数组
        ByteBuffer[] buffers = new ByteBuffer[2];
        buffers[0].allocate(5);
        buffers[1].allocate(3);

        // 等待客户端连接
        SocketChannel socketChannel = serverSocketChannel.accept();
        int messageLength = 8;

        while(true) {
            int byteRead = 0;
            while (byteRead < messageLength) {
                long l = socketChannel.read(buffers);
                // 累计读取的字节数
                byteRead += l;
                System.out.println("byteRead" + byteRead);
                // 流形式打印
                Arrays.asList(buffers).stream().map(buffer -> "position=" + buffer.position() +
                        ", limit=" + buffer.position()).forEach(System.out :: println);
            }

            // flip
            Arrays.asList(buffers).forEach(buffer -> buffer.flip());

            // 将数据读出显示到客户端
            int byteWrite = 0;
            while (byteWrite < messageLength) {
                long ll = socketChannel.write(buffers);
                // 累计读取的字节数
                byteWrite += ll;
               // 所有的buffer进行clear
                Arrays.asList(buffers).forEach(buffer -> buffer.clear());
                System.out.println("byteRead:" + byteRead + ", byteWrite:" + byteWrite) ;
            }
        }
    }
}
