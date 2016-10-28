package com.example.qjm3662.android5study.Socket;

/**
 * Created by qjm3662 on 2016/10/26 0026.
 */

import com.example.qjm3662.android5study.FileManager.FileUtils;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 服务器
 */
public class Server extends ServerSocket {

    private static final int PORT = 14538;

    private ServerSocket server;
    public static Socket client;
    private DataInputStream dis;
    private FileOutputStream fos;

    public Server(ServerListener serverListener, boolean IsGroup) throws Exception {
        try {
            try {
                if(!IsGroup){
                    server = new ServerSocket(PORT + 1);
                }
                server = new ServerSocket(PORT);
                while (true) {
                    System.out.println("等待客户端接入");
                    client = server.accept();
                    System.out.println("客户端已接入");
                    dis = new DataInputStream(client.getInputStream());
                    //文件名和长度
                    boolean isgroup = dis.readBoolean();
                    if(isgroup == IsGroup){
                        continue;
                    }
                    String fileName = dis.readUTF();
                    long fileLength = dis.readLong();
                    fos = new FileOutputStream(new File(FileUtils.getSDPath() + "/" + fileName));

                    byte[] sendBytes = new byte[1024];
                    int transLen = 0;
                    System.out.println("----开始接收文件<" + fileName + ">,文件大小为<" + fileLength + ">----");
                    serverListener.FileInfoCallback(fileName, FileUtils.getSDPath() + "/" + fileName);
                    while (true) {
                        int read = 0;
                        read = dis.read(sendBytes);
                        if (read == -1)
                            break;
                        transLen += read;
                        System.out.println("接收文件进度" + 100 * transLen / fileLength + "%...");
                        serverListener.FileProgressCallback((int) (100 * transLen / fileLength));
                        fos.write(sendBytes, 0, read);
                        fos.flush();
                    }
                    System.out.println("----接收文件<" + fileName + ">成功-------");
                    client.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (dis != null)
                    dis.close();
                if (fos != null)
                    fos.close();
                server.close();
            }
            System.out.println("Server Over !!!!----------------------------------------------");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static Socket getCurrentClient(){
        return client;
    }
    public interface ServerListener{
        void FileInfoCallback(String fileName, String path);
        void FileProgressCallback(int progress);
    }
}
