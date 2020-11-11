package com.hujtb.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author: hujtb
 * @Date: 2020/11/10 14:45
 */
public class BIOServer {
    public static void main(String[] args) throws IOException {
        // 创建线程池
        ExecutorService pool = Executors.newCachedThreadPool();
        // 创建服务端的serversocket
        ServerSocket serverSocket = new ServerSocket(6666);
        System.out.println("服务器启动了");

        // 监听，等待客户端连接
        while (true) {
            final Socket socket = serverSocket.accept();
            System.out.println("有客户端连接了");
            // 有客户端连接，开启一个线程
            pool.execute(new Runnable() {
                public void run() {
                    //和客户端通讯
                    handler(socket);
                }
            });
        }
    }

    /**
     * 编写一个方法和客户端通信
     */
    public static void handler(Socket socket) {
        System.out.println("currentThread" + Thread.currentThread().getId()
                + "----" + Thread.currentThread().getName());
        byte[] bytes = new byte[1024];
        InputStream inputStream = null;

        // 通过socket获取输入流
        try {
            inputStream = socket.getInputStream();
            // 循环读取客户端发送的数据
            while (true) {
                int read = inputStream.read(bytes);
                if (read != -1) {
                    System.out.println(new String(bytes, 0, read));
                } else {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
