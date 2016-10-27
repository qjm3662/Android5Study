package com.example.qjm3662.android5study.Socket;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.qjm3662.android5study.R;

public class SocketTest extends AppCompatActivity implements View.OnClickListener {

    private EditText et_data;
    private Button btn_send;
    private Button btn_connect;
    private Button btn_disConnect;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket_test);

        initView();
    }

    private void initView() {
        et_data = (EditText) findViewById(R.id.et_data);
        btn_send = (Button) findViewById(R.id.btn_send);
        btn_connect = (Button) findViewById(R.id.btn_connect);
        btn_disConnect = (Button) findViewById(R.id.btn_disConnect);
        tv = (TextView) findViewById(R.id.textView2);
        btn_connect.setOnClickListener(this);
        btn_disConnect.setOnClickListener(this);
        btn_send.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        try {
//            /** 关闭Socket*/
//            printWriter.close();
//            bufferedReader.close();
//            socket.close();
//            System.out.println("Socket unUonnected");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }

//    Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            switch (msg.what) {
//                case 0:
//                    try {
//                        printWriter = new PrintWriter(socket.getOutputStream(), true);
//                        /** 用于获取服务端传输来的信息 */
//                        // 由Socket对象得到输入流，并构造相应的BufferedReader对象
//                        bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//                        tv.setText("连接服务器成功");
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                        tv.setText("连接服务器失败");
//                    }
//                    break;
//            }
//        }
//    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send:
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        //创建一个流套接字并连接
//                        try {
//                            String data = et_data.getText().toString();
//                            /** 发送客户端准备传输的信息 */
//                            // 由Socket对象得到输出流，并构造PrintWriter对象
//                            // 将输入读入的字符串输出到Server
//                            printWriter.println(data);
//                            // 刷新输出流，使Server马上收到该字符串
//                            printWriter.flush();
//                            // 输入读入一字符串
//                            String result = bufferedReader.readLine();
//                            System.out.println("Server say : " + result);
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }).start();


                break;
            case R.id.btn_connect:
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            socket = new Socket("192.168.1.104", 14538);
//                            socket.setSoTimeout(60000);
//                            handler.sendEmptyMessage(0);
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                }).start();
                break;
            case R.id.btn_disConnect:
//                try {
//                    /** 关闭Socket*/
//                    printWriter.close();
//                    bufferedReader.close();
//                    socket.close();
//                    System.out.println("Socket unUonnected");
//                    tv.setText("断开服务器成功");
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    tv.setText("断开服务器失败");
//                }
                break;
        }
    }
}
