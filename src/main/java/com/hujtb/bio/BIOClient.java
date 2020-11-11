package com.hujtb.bio;

import java.io.IOException;
import java.net.Socket;

/**
 * @Author: hujtb
 * @Date: 2020/11/10 15:32
 */
public class BIOClient {
    public static void main(String[] args) throws IOException {
        Socket localhost = new Socket("localhost", 6666);
    }
}
