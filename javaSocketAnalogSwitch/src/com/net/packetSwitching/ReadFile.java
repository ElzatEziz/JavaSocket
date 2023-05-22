package com.net.packetSwitching;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ReadFile {
    private File file ; // 文件

    public FileInputStream readFile(String path) throws FileNotFoundException {
        // 读取文件
        file = new File(path);
        return new FileInputStream(file);
    }
    public int getFileSize() {
        // 获取文件大小
        return (int) file.length();
    }

    // 将文件分装成数据包
    public Packet packetFile(int sequenceNumber, byte[] buffer, boolean isLast, String fileName, int readSize) {
        return new Packet(sequenceNumber, buffer, isLast, fileName, readSize);
    }


    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
