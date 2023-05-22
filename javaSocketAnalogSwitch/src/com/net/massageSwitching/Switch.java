package com.net.massageSwitching;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Switch {


    private HashMap<String, String> routingTable; // 路由表

    public Switch() {
        // 初始化路由表
        routingTable = new HashMap<String, String>();
        routingTable.put("localhost", "127.0.0.1");
        routingTable.put("192.168.1.1", "192.168.1.2"); // 目标 IP 为 192.168.1.1 的数据包，下一跳为 192.168.1.2
        routingTable.put("192.168.1.3", "192.168.1.4"); // 目标 IP 为 192.168.1.3 的数据包，下一跳为 192.168.1.4
    }

    public void start(int port) throws Exception {
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Switch 已启动,等待连接");
        boolean isLastPacket = false;
        Socket senderSocket = serverSocket.accept(); // 等待发送端连接
        System.out.println("发送端已连接");
        ObjectInputStream objectInputStream = objectInputStream = new ObjectInputStream(senderSocket.getInputStream());

        // 读取初始化数据
        DataGram initDataGram = (DataGram) objectInputStream.readObject();
        String destinationIP = getDestinationIP(initDataGram); // 获取目标 IP
        if (destinationIP == null) {
            System.out.println("目标 IP 不存在");
            senderSocket.close();
            return;
        }
        System.out.println("目标 IP 为 " + destinationIP);
        // 连接下一跳
        Socket receiverSocket = new Socket(destinationIP, 7777);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(receiverSocket.getOutputStream());


        System.out.println("准备发送数据");
        while (!isLastPacket) {
            // 接收数据包
            DataGram dataGram = (DataGram) objectInputStream.readObject(); // 读取数据包
            System.out.println(dataGram);

            objectOutputStream.writeObject(dataGram); // 发送数据包
            objectOutputStream.flush();
            isLastPacket = dataGram.isLastPacket();
        }
        // 关闭连接
        objectInputStream.close();
        senderSocket.close();

    }

    private String getDestinationIP(DataGram dataGram) {
        // 返回目标 IP
        String destinationIP = dataGram.getDestinationIP();
        if (routingTable.containsKey(destinationIP)) {
            return routingTable.get(destinationIP);
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        Switch switchObj = new Switch();
        switchObj.start(8888);
    }
}

