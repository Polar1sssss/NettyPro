package com.hujtb.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Author: hujtb
 * @Date: 2020/11/11 14:36
 */
public class NIOFileChannel03 {
    public static void main(String[] args) throws Exception{
        FileInputStream fileInputStream = new FileInputStream("1.txt");
        FileChannel fileChannel01 = fileInputStream.getChannel();

        FileOutputStream fileOutputStream = new FileOutputStream("2.txt");
        FileChannel fileChannel02 = fileOutputStream.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(512);

        while(true) {
            // 复位buffer中的属性，***重要，否则position和limit相等会无限读出0
            buffer.clear();
            // 通道中的数据读取到缓冲中
            int read = fileChannel01.read(buffer);
            if (read == -1) {
                break;
            }
            buffer.flip();
            // 缓冲中的数据写入到channel中
            fileChannel02.write(buffer);
        }
        fileInputStream.close();
        fileOutputStream.close();
    }
}
