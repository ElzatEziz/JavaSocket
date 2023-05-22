package com.net.packetSwitching;

import java.io.*;
import java.net.*;

import static com.net.packetSwitching.Packet.PORT_RECEIVER;

public class Receiver {
    public static void main(String[] args) {

        try {
            // 创建接收端Socket对象
            ServerSocket serverSocket = new ServerSocket(PORT_RECEIVER);
            System.out.println("接收端已启动，等待数据传输...");

            Socket socket = serverSocket.accept();


            int sequenceNumber = 1; // 分组序号
            // 创建文件输出流
            FileOutputStream fileOutputStream = new FileOutputStream(new File("/Applications/Programming_Project/TestFile/testImg2.jpg"));
            // 创建标志位
            boolean receivedLastPacket = false; // 是否接收到最后一个分组

            // 接收初始分组数据报
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            Packet initPacket = (Packet) objectInputStream.readObject();

            // 如果分组序号为0，说明这是第一个分组，需要获取文件大小，缓冲区大小
            if (initPacket.getSequenceNumber() == 0) {
                String fileName = initPacket.getFileName();
                System.out.println("文件为：" + fileName + "，文件大小为：" + initPacket.getFileSize()+ " 字节 ,缓冲区大小为：" + initPacket.getBufferSize() + " 字节");
                // 发送确认消息
                String ackMsg = "已接收 初始分组";
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                objectOutputStream.writeObject(ackMsg);
                objectOutputStream.flush();
            }

            long time = 0;
            long start = System.currentTimeMillis();
            // 循环接收数据
            while (!receivedLastPacket) {
                // 接收分组数据报
                Packet packet = (Packet) objectInputStream.readObject();

                // 如果接收到最后一个分组，设置标志位
                if (packet.isLastPacket()) {
                    receivedLastPacket = true;
                     time= packet.getTime();
                    System.out.println("接收到最后一个分组 " + packet.getSequenceNumber());
                    continue;
                }

                // 从分组数据报中解析出sequenceNumber
                sequenceNumber = packet.getSequenceNumber();

                // 如果分组序号与期望序号相同，则将数据写入文件
                if (packet.getSequenceNumber() == sequenceNumber) {
                    byte[] dataCopy = packet.getData();
                    fileOutputStream.write(dataCopy, 0, packet.getDataLength());
                    System.out.println("接收到分组 " + sequenceNumber++);
                }
                // 如果分组序号与期望序号不同，则要求重发
                else {
                    System.out.println("接收到分组 " + packet.getSequenceNumber() + "，但期望接收到分组 " + sequenceNumber + "，要求重发。");
                }

            }

            // 已获取分组个数
            System.out.println("已接收到 " + sequenceNumber + " 个分组。期望接收到 " + initPacket.getPacketCount() + " 个分组。");

            //传播时延
            long end = System.currentTimeMillis();
            System.out.println("传播时延为：" + ((end - start)-time) + "ms");

            fileOutputStream.close();
            socket.close();
            serverSocket.close();
            System.out.println("接收完毕，文件保存在本地。");

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
