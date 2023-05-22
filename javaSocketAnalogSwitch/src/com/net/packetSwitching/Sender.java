package com.net.packetSwitching;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Sender {
    public static void main(String[] args) throws Exception {

        try {
            // 创建发送端Socket对象
            Socket socket = new Socket(InetAddress.getLocalHost().getHostAddress(), Packet.PORT_SWITCH);
            System.out.println("发送端已启动，准备数据传输...");

            // 创建文件读取对象
            ReadFile readFile = new ReadFile();
            FileInputStream fileInputStream = readFile.readFile("/Applications/Programming_Project/JavaProject/javaSocketAnalongSwitch/src/testImg.jpg");
            System.out.println("文件读取完毕，请用户选择分组大小(1024、2048、5120)：");
            Scanner scanner = new Scanner(System.in);
            int bufferSize = scanner.nextInt();
            // 获取文件大小
            int fileSize = readFile.getFileSize();
            System.out.println("文件大小为：" + fileSize + " 字节");


            System.out.println("开始发送文件数据分组");

            // 获取文件分组数量
            int packetNumber = (int) Math.ceil((double) fileSize / bufferSize) + 1;
            System.out.println("文件分组数量为：" + packetNumber + " 个");

            // 初始数据包处理时延
            long start = System.currentTimeMillis();
            // 发送初始化分组
            byte[] InitBytes = new byte[]{0};
            Packet initPacket = new Packet(0, InitBytes, false, readFile.getFile().getName(), InitBytes.length, fileSize, bufferSize);
            initPacket.setPacketCount(packetNumber);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(initPacket);
            objectOutputStream.flush();
            long end = System.currentTimeMillis();
            System.out.println("初始化数据处理时延 ：" + (end - start) + " ms");

            // 接受确认消息
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            String askMsg = (String) objectInputStream.readObject();
            System.out.println(askMsg);

            System.out.println("开始发送文件数据分组");
            // 文件数据分组的处理时延
            start = System.currentTimeMillis();
            // 发送文件内容
            int sequenceNumber = 1; // 分组序号
            byte[] buffer = new byte[bufferSize]; // 缓冲区
            int readSize = 0; // 每次读取的字节数
            while ((readSize = fileInputStream.read(buffer)) != -1) {
                Packet packet = new Packet(sequenceNumber, buffer, false, readFile.getFile().getName(), readSize);
                packet.setNextIP(InetAddress.getLocalHost().getHostAddress());
                sequenceNumber += 1;
                objectOutputStream.writeObject(packet);
                objectOutputStream.flush();
            }
            end = System.currentTimeMillis();
            System.out.println("文件数据处理时延 ：" + (end - start) + " ms");


            // 发送最后一个分组
            Packet lastPacket = new Packet(sequenceNumber, new byte[]{0}, true, readFile.getFile().getName(), readSize);
            lastPacket.setTime(end - start);
            sequenceNumber += 1;
            objectOutputStream.writeObject(lastPacket);
            objectOutputStream.flush();


            // 接收最后一个确认消息
            String lastAskMsg = (String) objectInputStream.readObject();
            System.out.println(lastAskMsg);

            fileInputStream.close();
            socket.close();
            System.out.println("文件发送完毕。");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}