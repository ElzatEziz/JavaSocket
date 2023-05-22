package com.net.massageSwitching;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Arrays;

public class DataGram implements Serializable {
    // 可序列化
    private static final long serialVersionUID = 1L;
    private String sourceIP; // 源 IP 地址
    private String destinationIP; // 目标 IP 地址
    private byte[] data; // 数据

    private int length; // 数据长度

    private boolean isLastPacket; // 是否为最后一个分组

    public DataGram(String sourceIP, String destinationIP, byte[] data , int length , boolean isLastPacket) {
        this.sourceIP = sourceIP;
        this.destinationIP = destinationIP;
        this.data = Arrays.copyOf(data, data.length);
        this.length = length;
        this.isLastPacket = isLastPacket;
    }

    public void setSourceIP(String sourceIP) {
        this.sourceIP = sourceIP;
    }

    public void setDestinationIP(String destinationIP) {
        this.destinationIP = destinationIP;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getSourceIP() {
        return sourceIP;
    }

    public String getDestinationIP() {
        return destinationIP;
    }

    public byte[] getData() {
        return data;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public boolean isLastPacket() {
        return isLastPacket;
    }

    public void setLastPacket(boolean lastPacket) {
        isLastPacket = lastPacket;
    }

    @Override
    public String toString() {
        return "DataGram{" +
                "sourceIP='" + sourceIP + '\'' +
                ", destinationIP='" + destinationIP + '\'' +
                ", data=" + Arrays.toString(data) +
                ", length=" + length +
                ", isLastPacket=" + isLastPacket +
                '}';
    }
}
