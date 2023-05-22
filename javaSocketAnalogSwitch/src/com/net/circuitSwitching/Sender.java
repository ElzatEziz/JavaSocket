package com.net.circuitSwitching;

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Sender {

    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("localhost", 5555);
        System.out.println("发送端：连接建立。远程地址:" + socket.getRemoteSocketAddress());

        //获取文件
        File file = new File("/Applications/Programming_Project/JavaProject/javaSocketAnalongSwitch/src/testImg.jpg");
        FileInputStream fileInputStream = new FileInputStream(file);

        // 用户输入发送文件大小
        System.out.println("请输入分组大(1024,2048,5120):");
        Scanner scanner = new Scanner(System.in);
        int bufferSize = scanner.nextInt();


        // 建立数据输入输出流
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(socket.getOutputStream());
        // 发送消息
        byte[] buffer = new byte[bufferSize];
        int readSize = 0;
        while ((readSize = fileInputStream.read(buffer)) != -1) {
            bufferedOutputStream.write(buffer,0,readSize);
            bufferedOutputStream.flush();
        }

        System.out.println("发送端：发送完毕。");
        // 关闭连接
        fileInputStream.close();
        bufferedOutputStream.close();
        socket.close();
        System.out.println("发送端：连接关闭。");
    }
}

