package com.net.circuitSwitching;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;

import java.net.ServerSocket;
import java.net.Socket;

public class Switch {
    public static void main(String[] args) throws Exception {

        //建立连接
        ServerSocket serverSocket = new ServerSocket(5555);
        System.out.println("交换机:等待连接…");
        Socket sender = serverSocket.accept();
        System.out.println("交换机:建立连接. 远程地址: " + sender.getRemoteSocketAddress());

        //建立数据输入输出流
        BufferedInputStream bufferedInputStream = new BufferedInputStream(sender.getInputStream());
        //连接交换机
        Socket receiver = new Socket("localhost", 6666);
        System.out.println("交换机:建立连接. 远程地址: " + receiver.getRemoteSocketAddress());

        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(receiver.getOutputStream());

        //接收消息
        send(bufferedInputStream, bufferedOutputStream );

        //关闭连接
        bufferedInputStream.close();
        bufferedOutputStream.close();
        sender.close();
        receiver.close();
        System.out.println("交换机:连接关闭.");

    }

    public static void send(BufferedInputStream bufferedInputStream, BufferedOutputStream bufferedOutputStream) throws Exception {
       int readLen;
       int bufferSize = 1024;
       if(bufferedInputStream.available() != 0) {
           bufferSize = bufferedInputStream.available();
       }
        byte[] bytes = new byte[bufferSize];
        while ((readLen = bufferedInputStream.read(bytes)) != -1) {
            bufferedOutputStream.write(bytes , 0 , readLen);
            bufferedOutputStream.flush();
        }
        System.out.println("数据发送完毕");
    }
}
