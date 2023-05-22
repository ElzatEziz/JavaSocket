package com.net.massageSwitching;

import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Receiver {
    public static void main(String[] args) throws Exception {
        // 创建连接
        ServerSocket serverSocket = new ServerSocket(7777);
        System.out.println("接收端已启动,等待连接");
        Socket socket = serverSocket.accept();
        System.out.println("接收端已连接");


        // 文件输出
        FileOutputStream fileOutputStream = new FileOutputStream("/Applications/Programming_Project/TestFile/testImg4.jpg");
        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

        //传播时延
        long time = 0;
        long start = System.currentTimeMillis();
        System.out.println("开始接受文件");
        // 接收文件
        while (true) {
            DataGram dataGram = (DataGram)objectInputStream.readObject();
            fileOutputStream.write(dataGram.getData(), 0, dataGram.getLength());
            if (dataGram.isLastPacket()) {
                System.out.println("最后一个分组已接收");
                break;
            }
        }
        System.out.println("文件接受完毕");
        time = System.currentTimeMillis() - start;
        System.out.println("传播时延为：" + time + "ms");

        // 关闭连接
        fileOutputStream.close();
        socket.close();

    }
}
