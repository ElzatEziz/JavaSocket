package com.net.packetSwitching;

import java.io.*;
import java.net.*;

public class Switch {
    public static void main(String[] args) throws Exception {
        final int BUFFER_SIZE = 1024; // 缓冲区大小

        try {
            ServerSocket serverSocket = new ServerSocket(Packet.PORT_SWITCH); // 创建交换机Socket对象
            System.out.println("交换机已启动，等待数据传输...");
            Socket socket = serverSocket.accept(); // 获取发送方Socket对象

            Socket receiverSocket = new Socket(InetAddress.getLocalHost(), Packet.PORT_RECEIVER); // 创建接收方Socket对象


            // 接收初始数据包
            ObjectInputStream senderInputStream = new ObjectInputStream(socket.getInputStream()); //发送方输入流
            Packet initPacket = (Packet)senderInputStream.readObject();



            // 发送初始确认消息到接受方
            ObjectOutputStream receiverOutputStream = new ObjectOutputStream(receiverSocket.getOutputStream()); //接收方输出流
            receiverOutputStream.writeObject(initPacket);
            receiverOutputStream.flush();
            System.out.println("已发送初始数据");

            // 获取接受方初始确认消息
            ObjectInputStream receiverInputStream = new ObjectInputStream(receiverSocket.getInputStream()); //接收方输入流
            String askMsg = (String)receiverInputStream.readObject();
            System.out.println("接受方已接收初始数据:" + askMsg);

            // 发送初始确认消息给发送方
            ObjectOutputStream senderOutputStream = new ObjectOutputStream(socket.getOutputStream()); //发送方输出流
            String initAckMsg = "初始化数据已接收。";
            senderOutputStream.writeObject(initAckMsg);
            senderOutputStream.flush();
            System.out.println("已接收初始确认消息并发送确认消息");

            // 接收分组数据包
            System.out.println("开始发送文件数据分组");
            byte[] packetData = new byte[BUFFER_SIZE];
            while (true) {
                // 从发送方接受数据包
                Packet packet = (Packet)senderInputStream.readObject();
                packet.setNextIP(InetAddress.getLocalHost().getHostAddress());

                // 打印数据包信息
                System.out.println(packet);
                // 转发数据包到接收方
                receiverOutputStream.writeObject(packet);
                receiverOutputStream.flush();

                // 如果是最后一个分组，则退出循环
                if (packet.isLastPacket()) {
                    // 发送最后一个确认消息给发送方
                    String lastAckMsg = "最后一组数据报已接收。";
                    senderOutputStream.writeObject(lastAckMsg);
                    senderOutputStream.flush();
                    break;
                }
            }
            socket.close();
            serverSocket.close();
            System.out.println("数据转发完毕。");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
