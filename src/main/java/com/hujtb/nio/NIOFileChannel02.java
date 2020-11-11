package com.hujtb.nio;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Author: hujtb
 * @Date: 2020/11/11 11:28
 */
public class NIOFileChannel02 {
    public static void main(String[] args) {
        FileInputStream fileInputStream = null;
        File file = new File("d:\\file01.txt");
        try {
            // 创建一个输入流
            fileInputStream = new FileInputStream(file);
            FileChannel channel = fileInputStream.getChannel();
            // 创建一个缓冲区
            ByteBuffer byteBuffer = ByteBuffer.allocate((int)file.length());
            // 将通道的数据读取到缓冲区
            channel.read(byteBuffer);
            // 对缓冲区进行flip
            byteBuffer.flip();
            // 打印缓冲区的数据
            System.out.println(new String(byteBuffer.array(), 0, byteBuffer.capacity()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
