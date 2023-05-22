package com.net.circuitSwitching;

import java.net.*;
import java.io.*;

public class Receiver {
    public static void main(String[] args) throws Exception {
        // 建立连接
        ServerSocket serverSocket = new ServerSocket(6666);
        System.out.println("接收端：等待连接…");
        Socket socket = serverSocket.accept();
        System.out.println("接收端：连接建立。远程地址:" + socket.getRemoteSocketAddress());

        // 建立数据输入输出流
        BufferedInputStream bufferedInputStream = new BufferedInputStream(socket.getInputStream());

        //文件输出流
        File file = new File("/Applications/Programming_Project/TestFile/testImg3.jpg");
        FileOutputStream fileOutputStream = new FileOutputStream(file);


        // 接收消息
        receive(bufferedInputStream, fileOutputStream);

        // 关闭连接
        bufferedInputStream.close();
        fileOutputStream.close();
        socket.close();
        System.out.println("接收端：连接关闭。");

    }

    private static void receive(BufferedInputStream bufferedInputStream, FileOutputStream fileOutputStream) throws IOException, ClassNotFoundException {
        // 接收消息
        int readLen;
        int bufferSize=1024;
        if(bufferedInputStream.available() != 0) {
            bufferSize = bufferedInputStream.available();
        }
        byte[] bytes = new byte[bufferSize];
        while ((readLen = bufferedInputStream.read(bytes))!= -1) {
            fileOutputStream.write(bytes , 0 , readLen);
            fileOutputStream.flush();
        }
    }
}

