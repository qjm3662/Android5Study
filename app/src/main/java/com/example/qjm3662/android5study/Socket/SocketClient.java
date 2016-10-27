package com.example.qjm3662.android5study.Socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by qjm3662 on 2016/10/26 0026.
 */

public class SocketClient extends Socket{
    private static final String SERVER_IP = "192.168.1.104";
    private static final int SERVER_PORT = 14538;

    private Socket client;
    private PrintWriter out;
    private BufferedReader in;

    /**
     * 与服务器连接，并输入发送消息
     */
    public SocketClient() throws Exception{
        super(SERVER_IP, SERVER_PORT);
        client = this;
        out = new PrintWriter(this.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(this.getInputStream()));
        new readLineThread();
    }

    public void sendMessage(String msg){
        try {
            out = new PrintWriter(this.getOutputStream(), true);
            out.println(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.close();
    }

    /**
     * 用于监听服务器端向客户端发送消息线程类
     */
    class readLineThread extends Thread{

        private BufferedReader buff;
        public readLineThread(){
            try {
                buff = new BufferedReader(new InputStreamReader(client.getInputStream()));
                start();
            } catch (Exception e) {
            }
        }

        @Override
        public void run() {
            try {
                while(true){
                    String result = buff.readLine();
                    if("byeClient".equals(result)){//客户端申请退出，服务端返回确认退出
                        break;
                    }else{//输出服务端发送消息
                        System.out.println(result);
                    }
                }
                in.close();
                out.close();
                client.close();
            } catch (Exception e) {
            }
        }
    }

}
