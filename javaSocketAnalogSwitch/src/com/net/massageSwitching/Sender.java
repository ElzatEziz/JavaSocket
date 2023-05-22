package com.net.massageSwitching;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Sender {
    public static void main(String[] args) throws Exception {
        // 创建连接
        String destinationIP = "localhost";
        Socket socket = new Socket(destinationIP, 8888);
        System.out.println("发送端已启动,正在连接");

        // 读取文件
        FileInputStream fileInputStream = new FileInputStream(new File("/Applications/Programming_Project/JavaProject/javaSocketAnalongSwitch/src/testImg.jpg"));
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        System.out.println("文件获取完毕");

        //发送端初始化处理时延
        long start = System.currentTimeMillis();
        // 发送初始化数据包
        DataGram initDatagram = new DataGram("localhost", destinationIP, new byte[]{0}, 1, false);
        objectOutputStream.writeObject(initDatagram);
        objectOutputStream.flush();
        System.out.println("初始化数据包发送完毕");
        long end = System.currentTimeMillis();
        System.out.println("发送端初始化处理时延为：" + (end - start) + "ms");

        System.out.println("开始发送文件");

        //发送端发送文件数据处理时延
        start = System.currentTimeMillis();

        // 发送文件
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = fileInputStream.read(buffer)) != -1) {
            DataGram dataGram = new DataGram("localhost", destinationIP, buffer ,bytesRead , false);
            objectOutputStream.writeObject(dataGram);
            objectOutputStream.flush();
        }

        System.out.println("文件发送完毕");

        System.out.println("正在发送最后一个分组");

        // 发送最后一个分组
        byte[] lastBuffer = new byte[]{0};
        DataGram dataGram = new DataGram("localhost", destinationIP, lastBuffer , lastBuffer.length, true);
        objectOutputStream.writeObject(dataGram);
        objectOutputStream.flush();

        System.out.println("最后一个分组发送完毕");
        end = System.currentTimeMillis();
        System.out.println("发送端发送文件数据处理时延为：" + (end - start) + "ms");

        // 关闭连接
        fileInputStream.close();
        objectOutputStream.close();
        socket.close();
        socket.close();


    }
}
