package com.example.qjm3662.android5study;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.qjm3662.android5study.Socket.MySocketClient;
import com.example.qjm3662.android5study.WifiDirect.MyWifiDirectActivity;
import com.example.qjm3662.android5study.WifiDirect.TransMain;

public class MainActivity extends AppCompatActivity {

    private Button btn_send;
    private Button btn_gallery;
    private Button btn_tab;
    private Button btn_menu;
    private Button btn_bitmap;
    private Button btn_wifiDemo;
    private Button btn_socket;
    private Button btn_TransMain;

    public static final int FILE_SELECT_CODE = 1;
    public static final String FILE_SELECT = "file select";
    public static final int requestCode_selectFile = 6;
    public static final int resultCode = 9;
    public static final String PATH = "path";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_send = (Button) findViewById(R.id.btn_send);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AutoCompleteTextActivity.class));
            }
        });

        btn_gallery = (Button) findViewById(R.id.btn_Gallery);
        btn_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, GalleryActivity.class));
            }
        });

        btn_tab = (Button) findViewById(R.id.btn_tab);
        btn_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TabDemoActivity.class));
            }
        });

        btn_menu = (Button) findViewById(R.id.btn_menu);
        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MenuDemo.class));
            }
        });

        btn_bitmap = (Button) findViewById(R.id.btn_bitmap);
        btn_bitmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, BitmapDemoActivity.class));
            }
        });


        btn_wifiDemo = (Button) findViewById(R.id.btn_wifiDemo);
        btn_wifiDemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MyWifiDirectActivity.class));
            }
        });

        btn_socket = (Button) findViewById(R.id.btn_socket);
        btn_socket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MySocketClient.class));
            }
        });


        btn_TransMain = (Button) findViewById(R.id.btn_TransMain);
        btn_TransMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TransMain.class));
            }
        });
        Button btn_notification = (Button) findViewById(R.id.btn_notification);
        btn_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getApplicationContext();
                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                int icon = R.drawable.icon2;
                CharSequence title = "这是一个Notification";
                long when = System.currentTimeMillis();
                Notification.Builder builder = new Notification.Builder(context);
                builder.setSmallIcon(icon);
                builder.setTicker(title);
                builder.setWhen(when);
                Notification notification = builder.getNotification();
                CharSequence contentTitle = "这是标题";
                CharSequence contentText = "这只是个测试";
                Intent notificationIntent = new Intent(context, TabDemoActivity.class);
                PendingIntent contentPending = PendingIntent.getActivity(MainActivity.this, 0, notificationIntent, 0);
                notification.defaults = Notification.DEFAULT_SOUND;
                manager.notify(11, notification);
            }
        });

    }
}
