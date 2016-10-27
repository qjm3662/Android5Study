package com.example.qjm3662.android5study.Socket;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qjm3662.android5study.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class MySocketClient extends AppCompatActivity implements View.OnClickListener {

    private EditText et_ip;
    private EditText et_content;
    private TextView tv_content;
    private Button btn_send;
    private Button btn_connect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_socket_client);

        initView();
    }

    private void initView() {
        et_ip = (EditText) findViewById(R.id.et_ip);
        et_content = (EditText) findViewById(R.id.et_content);
        tv_content = (TextView) findViewById(R.id.tv);
        btn_send = (Button) findViewById(R.id.btn_send);
        btn_connect = (Button) findViewById(R.id.btn_connect);

        btn_connect.setOnClickListener(this);
        btn_send.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send:
                send();
                break;
            case R.id.btn_connect:
                connect();
                break;
        }
    }


    //-----------------------------------------------------------------------

    private Socket socket = null;
    private BufferedWriter writer = null;
    private BufferedReader reader = null;

    private void connect() {
        Toast.makeText(this, "建立链接成功", Toast.LENGTH_SHORT).show();
        final AsyncTask<Void, String, Void> read = new AsyncTask<Void, String, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    socket = new Socket("192.168.1.104", 14538);
                    writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    publishProgress("@success");
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(MySocketClient.this, "无法建立链接", Toast.LENGTH_SHORT).show();
                }
                String line = null;
                try {
                    while ((line = reader.readLine()) != null) {
                        System.out.println("readline----------------------------------");
                        publishProgress(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onProgressUpdate(String... values) {
                if(values[0].equals("@success")){
                    tv_content.append("@success\n");
                }
                tv_content.append("别人说：" + values[0] + "\n");
                super.onProgressUpdate(values);
            }
        };
        read.execute();

    }

    private void send() {
        try {
            tv_content.append("我说：" + et_content.getText().toString() + "\n");
            writer.write(et_content.getText().toString() + "\n");
            writer.flush();
            et_content.setText("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
