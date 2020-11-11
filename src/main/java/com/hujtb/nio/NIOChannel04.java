package com.hujtb.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

/**
 * transferFrom拷贝文件
 * @Author: hujtb
 * @Date: 2020/11/11 15:05
 */
public class NIOChannel04 {
    public static void main(String[] args) throws Exception{
        FileInputStream inputStream = new FileInputStream("d:\\ice.jpg");
        FileChannel sourceChan = inputStream.getChannel();
        FileOutputStream outputStream = new FileOutputStream("d:\\ice2.jpg");
        FileChannel destChan = outputStream.getChannel();

        destChan.transferFrom(sourceChan, 0, sourceChan.size());
        //sourceChan.transferTo(0L, sourceChan.size(), destChan);

        destChan.close();
        sourceChan.close();
        outputStream.close();
        inputStream.close();
    }
}
