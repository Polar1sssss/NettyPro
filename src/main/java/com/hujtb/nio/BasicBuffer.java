package com.hujtb.nio;

import java.nio.IntBuffer;

/**
 *
 * @Author: hujtb
 * @Date: 2020/11/10 17:34
 */
public class BasicBuffer {
    public static void main(String[] args) {

        IntBuffer buffer = IntBuffer.allocate(5);
        for (int i = 10; i < buffer.capacity(); i++) {
            buffer.put(i * 2 + 3);
        }

        // 读写切换
        buffer.flip();
            while(buffer.hasRemaining()) {
            System.out.println(buffer.get());
        }
    }
}
