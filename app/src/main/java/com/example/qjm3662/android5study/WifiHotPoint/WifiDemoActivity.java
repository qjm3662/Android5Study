package com.example.qjm3662.android5study.WifiHotPoint;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qjm3662.android5study.FileManager.FileManager;
import com.example.qjm3662.android5study.MainActivity;
import com.example.qjm3662.android5study.R;
import com.example.qjm3662.android5study.WifiDirect.FileSendAsycn;
import com.example.qjm3662.android5study.WifiDirect.Server;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.Format;
import java.util.ArrayList;
import java.util.List;

public class WifiDemoActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private Button btn_open, btn_close, btn_check, btn_search;
    private TextView tv_content;
    private WifiManager wifiManager;
    private WifiInfo wifiInfo;
    private List<ScanResult> WifiList;
    private StringBuffer stringBuffer = new StringBuffer();
    private ListView listView;
    private WifiListAdapter adapter;
    // 网络连接列表
    private List<WifiConfiguration> mWifiConfiguration;
    private String ip;
    private boolean isAp = false;
    private Server server;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_demo);
        wifiManager = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
        initView();
    }

    private void initView() {
        btn_open = (Button) findViewById(R.id.btn_open);
        btn_close = (Button) findViewById(R.id.btn_close);
        btn_check = (Button) findViewById(R.id.btn_check);
        btn_search = (Button) findViewById(R.id.btn_search);
        tv_content = (TextView) findViewById(R.id.tv_content);

        btn_search.setOnClickListener(this);
        btn_close.setOnClickListener(this);
        btn_open.setOnClickListener(this);
        btn_check.setOnClickListener(this);
        listView = (ListView) findViewById(R.id.list);
        adapter = new WifiListAdapter(this, WifiList, wifiInfo);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    server = new Server(new Server.ServerListener() {
                        @Override
                        public void FileInfoCallback(String fileName, String path) {

                        }

                        @Override
                        public void FileProgressCallback(int progress) {

                        }
                    }, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_check:    //检查wifi状态
//                System.out.println("wifi state --->" + wifiManager.getWifiState());
//                Toast.makeText(this, "当前网卡状态为：" + getWifiState(), Toast.LENGTH_SHORT).show();
                try {
                    Intent intent = new Intent(this, FileManager.class);
                    intent.putExtra(MainActivity.FILE_SELECT, MainActivity.FILE_SELECT_CODE);
                    startActivityForResult(intent, 6);
                    overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                } catch (Exception e) {
                    System.out.println("打开错误 : " + e.toString());
                }
                break;
            case R.id.btn_search:   //扫描wifi
                startScan();
                adapter.setWifiInfo(wifiInfo);
                adapter.setWifiList(WifiList);

                adapter.notifyDataSetChanged();
                if (stringBuffer == null) {
                    stringBuffer = new StringBuffer();
                }
                printHotIp();
                ip = Formatter.formatIpAddress(wifiInfo.getIpAddress());
                String info = "ip：" + ip + "\nssid:" + wifiInfo.getSSID() + "\n";
                tv_content.setText(info);
                break;
            case R.id.btn_open:     //打开Wifi操作
                OpenWifi();
                System.out.println("wifi state --->" + wifiManager.getWifiState());
                Toast.makeText(this, "当前网卡状态为：" + getWifiState(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_close:
//                CloseWifi();
                final ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.show();
                progressDialog.setMessage("正在打开热点");
                progressDialog.onStart();
                WifiApAdmin apAdmin = new WifiApAdmin(this, new WifiApAdmin.WifiApAdminListener() {
                    @Override
                    public void createSuccess() {
                        progressDialog.dismiss();
                        isAp = true;
                    }

                    @Override
                    public void createFaild() {

                    }
                });
                apAdmin.startWifiAp("sdfs", "");
                System.out.println("wifi state --->" + wifiManager.getWifiState());
                Toast.makeText(this, "当前网卡状态为：" + getWifiState(), Toast.LENGTH_SHORT).show();
                break;
        }
    }



    public void printHotIp() {

        ArrayList<String> connectedIP = getConnectedHotIP();
        StringBuilder resultList = new StringBuilder();
        for (String ip : connectedIP) {
            resultList.append(ip);
            resultList.append("\n");
        }
        Toast.makeText(this, resultList, Toast.LENGTH_LONG).show();
        System.out.print(resultList);
        Log.d("kipeng","---->>heww resultList="+resultList);
    }


    private ArrayList<String> getConnectedHotIP() {
        ArrayList<String> connectedIP = new ArrayList<String>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(
                    "/proc/net/arp"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] splitted = line.split(" +");
                if (splitted != null && splitted.length >= 4) {
                    String ip = splitted[0];
                    connectedIP.add(ip);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connectedIP;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case MainActivity.requestCode_selectFile:
                try{
                    if (data != null) {
                        System.out.println("Path : " + data.getStringExtra(MainActivity.PATH));
                        FileSendAsycn fileSendAsycn;
                        if(isAp){
                            fileSendAsycn = new FileSendAsycn(isAp);
                            fileSendAsycn.execute(data.getStringExtra(MainActivity.PATH), getConnectedHotIP().get(0));
                            try {
                                server.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }else{
                            System.out.println("IP : " + ip);
                            try {
                                fileSendAsycn = new FileSendAsycn(true);
                                fileSendAsycn.execute(data.getStringExtra(MainActivity.PATH), ip);
                            } catch (Exception e) {
                                System.out.println("传送文件错误 ： " + e.toString());
                            }
                        }
                    }
                }catch (Exception e){
                    System.out.println(e.toString());
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void startScan() {
        //wifi开始扫描
        wifiManager.startScan();
        // 得到扫描结果
        WifiList = wifiManager.getScanResults();
        wifiInfo = wifiManager.getConnectionInfo();
        // 得到配置好的网络连接
        mWifiConfiguration = wifiManager.getConfiguredNetworks();
    }

    // 指定配置好的网络进行连接
    public void connectConfiguration(int index) {
        // 索引大于配置好的网络索引返回
        if (index > mWifiConfiguration.size()) {
            return;
        }
        // 连接配置好的指定ID的网络
        wifiManager.enableNetwork(mWifiConfiguration.get(index).networkId,
                true);

    }

    private void CloseWifi() {
        if (wifiManager != null) {
            //关闭wifi
            wifiManager.setWifiEnabled(false);
        }
    }

    private void OpenWifi() {
        if (wifiManager != null) {
            //打开wifi
            wifiManager.setWifiEnabled(true);
        }
    }

    private String getWifiState() {
        String temp = null;
        switch (wifiManager.getWifiState()) {
            case 0:
                temp = "Wifi正在关闭ING";
                break;
            case 1:
                temp = "Wifi已经关闭";
                break;
            case 2:
                temp = "Wifi正在打开ING";
                break;
            case 3:
                temp = "Wifi已经打开";
                break;
            default:
                break;
        }
        return temp;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, "正在连接至 ： " + WifiList.get(position).SSID, Toast.LENGTH_SHORT).show();
        connectConfiguration(position);
    }
}
