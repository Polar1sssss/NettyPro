package com.hujtb.nio;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Author: hujtb
 * @Date: 2020/11/11 11:28
 */
public class NIOFileChannel01 {
    public static void main(String[] args) {
        // 内存数据 -> 缓冲 -> 通道 -> 文件
        String str = "hello, niooooooo";
        FileOutputStream fileOutputStream = null;
        try {
            // 创建一个输出流->channel
            fileOutputStream = new FileOutputStream("d:\\file01.txt");
            // 通过fileOutPutStream获取对应的FileChannel
            // 这个fileChannel的真实类型是FileChannelImpl
            FileChannel channel = fileOutputStream.getChannel();
            // 创建一个缓冲区
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            // 将内存数据放入到缓冲区中
            byteBuffer.put(str.getBytes());
            // 对缓冲区进行flip
            byteBuffer.flip();
            // 将缓冲区数据写入到channel
            channel.write(byteBuffer);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
