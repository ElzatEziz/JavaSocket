package com.net.packetSwitching;

import java.io.Serializable;
import java.util.Arrays;

public class Packet implements Serializable {
    // 序列化版本号
    private static final long serialVersionUID = 1L;
    private int sequenceNumber; // 分组序号
    private byte[] data; // 数据
    private boolean isLastPacket; // 是否是最后一个分组
    private String fileName; // 文件名
    private int dataLength; // 数据长度
    private int bufferSize = 1024; // 缓冲区大小
    private int fileSize; // 文件大小

    private int packetCount; // 分组数量

    private long time ;//时延

    private String nextIP; // 下一跳IP

    public static final int PORT_RECEIVER = 6000; // 接收端口号
    public static final int PORT_SWITCH = 5500; // 交换机端口号
    public static final int PORT_SENDER = 4000; // 发送端口号

    public Packet(int sequenceNumber, byte[] data, boolean isLastPacket, String fileName, int dataLength, int fileSize, int bufferSize) {
        this.sequenceNumber = sequenceNumber;
        this.data = Arrays.copyOf(data, data.length);
        this.isLastPacket = isLastPacket;
        this.fileName = fileName;
        this.dataLength = dataLength;
        this.fileSize = fileSize;
        this.bufferSize = bufferSize;
    }

    public Packet(int sequenceNumber, byte[] data, boolean isLastPacket, String fileName, int dataLength) {
        this.sequenceNumber = sequenceNumber;
        this.data = Arrays.copyOf(data, data.length);
        this.isLastPacket = isLastPacket;
        this.fileName = fileName;
        this.dataLength = dataLength;
    }

    public int getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public boolean isLastPacket() {
        return isLastPacket;
    }

    public void setLastPacket(boolean lastPacket) {
        isLastPacket = lastPacket;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getDataLength() {
        return dataLength;
    }

    public void setDataLength(int dataLength) {
        this.dataLength = dataLength;
    }

    public int getFileSize() {
        return fileSize;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }

    public int getBufferSize() {
        return this.bufferSize;
    }

    public void setBufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
    }

    public int getPacketCount() {
        return packetCount;
    }

    public void setPacketCount(int packetCount) {
        this.packetCount = packetCount;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getNextIP() {
        return nextIP;
    }

    public void setNextIP(String nextIP) {
        this.nextIP = nextIP;
    }


    @Override
    public String toString() {
        return "Packet{" +
                "sequenceNumber=" + sequenceNumber +
                ", nextIP='" + nextIP + '\'' +
                ", fileName='" + fileName + '\'' +
                ", data=" + Arrays.toString(data) +
                ", isLastPacket=" + isLastPacket +
                ", dataLength=" + dataLength +
                ", fileSize=" + fileSize +
                ", bufferSize=" + bufferSize +
                '}';
    }
}

