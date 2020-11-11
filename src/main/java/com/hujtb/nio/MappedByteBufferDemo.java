package com.hujtb.nio;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Author: hujtb
 * @Date: 2020/11/11 16:08
 */
public class MappedByteBufferDemo {
    public static void main(String[] args) throws Exception{
        RandomAccessFile randomAccessFile = new RandomAccessFile("1.txt", "rw");
        // 获取对应的通道
        FileChannel channel = randomAccessFile.getChannel();
        /**
         * 参数一：使用读写模式
         * 参数二：可以直接修改的起始位置
         * 参数三：将文件的多少个字节映射到内存中，即可修改的范围，5个字节
         * 实际类型是DirectByteBuffer
         */
        MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);
        map.put(0, (byte) 'A');
        map.put(1, (byte) 'B');

        channel.close();
        randomAccessFile.close();
    }
}
