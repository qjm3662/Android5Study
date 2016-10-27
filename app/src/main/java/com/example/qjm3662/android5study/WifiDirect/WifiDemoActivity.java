package com.example.qjm3662.android5study.WifiDirect;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qjm3662.android5study.R;

import java.util.List;

public class WifiDemoActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_open, btn_close, btn_check, btn_search;
    private TextView tv_content;
    private WifiManager wifiManager;
    private WifiInfo wifiInfo;
    private ScrollView scrollView;
    private List WifiConfiguration;
    private ScanResult scanResult;
    private List<ScanResult> WifiList;
    private StringBuffer stringBuffer = new StringBuffer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_demo);
        wifiManager = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
        initView();
    }

    private void initView() {
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        btn_open = (Button) findViewById(R.id.btn_open);
        btn_close = (Button) findViewById(R.id.btn_close);
        btn_check = (Button) findViewById(R.id.btn_check);
        btn_search = (Button) findViewById(R.id.btn_search);
        tv_content = (TextView) findViewById(R.id.tv_content);

        btn_search.setOnClickListener(this);
        btn_close.setOnClickListener(this);
        btn_open.setOnClickListener(this);
        btn_check.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_check:    //检查wifi状态
                System.out.println("wifi state --->" + wifiManager.getWifiState());
                Toast.makeText(this, "当前网卡状态为：" + change(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_search:   //扫描wifi
                //wifi开始扫描
                wifiManager.startScan();
                WifiList = wifiManager.getScanResults();
                wifiInfo = wifiManager.getConnectionInfo();

                if (stringBuffer == null) {
                    stringBuffer = new StringBuffer();
                }
                stringBuffer = stringBuffer.append("Wifi名").append("    ").append("Wifi地址").append("    ")
                        .append("Wifi频率").append("    ").append("Wifi信号").append("\n");
                if (WifiList != null) {
                    for (ScanResult result : WifiList) {
                        stringBuffer = stringBuffer.append(result.SSID).append("  ")
                                .append(result.BSSID).append("  ")
                                .append(result.frequency).append("  ")
                                .append(result.level).append("\n");
                        tv_content.setText(stringBuffer.toString());
                    }
                }
                stringBuffer = stringBuffer.append("----------------------------------------------").append("\n");
                tv_content.setText(stringBuffer.toString());
                stringBuffer = stringBuffer.append("当前Wifi--BSSID :").append(wifiInfo.getBSSID()).append("\n")
                        .append("当前Wifi--HiddenSSID :").append(wifiInfo.getHiddenSSID()).append("\n")
                        .append("当前Wifi--IpAddress :").append(wifiInfo.getIpAddress()).append("\n")
                        .append("当前Wifi--MacAddress :").append(wifiInfo.getMacAddress()).append("\n")
                        .append("当前Wifi--NetWork ID :").append(wifiInfo.getNetworkId()).append("\n")
                        .append("当前Wifi--RSSI :").append(wifiInfo.getRssi()).append("\n")
                        .append("当前Wifi--SSID :").append(wifiInfo.getSSID()).append("\n")
                        .append("----------------------------------------------").append("\n")
                        .append("全部打印出关于本机Wifi信息:").append(wifiInfo.toString());
                tv_content.setText(stringBuffer.toString());
                break;
            case R.id.btn_open:     //打开Wifi操作
                //打开wifi
                wifiManager.setWifiEnabled(true);
                System.out.println("wifi state --->" + wifiManager.getWifiState());
                Toast.makeText(this, "当前网卡状态为：" + change(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_close:    //关闭wifi
                wifiManager.setWifiEnabled(false);
                System.out.println("wifi state --->" + wifiManager.getWifiState());
                Toast.makeText(this, "当前网卡状态为：" + change(), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private String change() {
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
}
