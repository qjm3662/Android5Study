package com.example.qjm3662.android5study.WifiDirect;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.p2p.WifiP2pManager;

/**
 * Created by qjm3662 on 2016/10/25 0025.
 */

public class WifiDirectBroadCastReceiver extends BroadcastReceiver{
    private WifiP2pManager manager;
    private WifiP2pManager.Channel channel;
    private MyWifiDirectActivity activity;

    public WifiDirectBroadCastReceiver(WifiP2pManager manager, WifiP2pManager.Channel channel, MyWifiDirectActivity activity, WifiP2pManager.PeerListListener peerListListener) {
        this.manager = manager;
        this.channel = channel;
        this.activity = activity;
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        switch (action){
            //当wifi功能打开关闭的时候会广播这个信号
            case WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION:
                System.out.println("WIFI_P2P_STATE_CHANGED_ACTION");
                int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
                if(state == WifiP2pManager.WIFI_P2P_STATE_ENABLED){
                    //Wifi Direct is enabled

                }else{
                    //Wifi Direct is not enabled
                }
                break;
            //获取当前可用连接表的列表
            case WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION:
                System.out.println("WIFI_P2P_PEERS_CHANGED_ACTION");
                if(manager != null){
                    manager.requestPeers(channel, activity.getPeersListener());
                }
                break;
            //当连接建立或者断开的时候会广播该信号
            case WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION:
                System.out.println("WIFI_P2P_CONNECTION_CHANGED_ACTION");
                if(manager == null){
                    return;
                }
                NetworkInfo networkInfo = intent.getParcelableExtra(WifiP2pManager.EXTRA_NETWORK_INFO);
                if(networkInfo.isConnected()){
                    System.out.println("已连接上");
                    manager.requestConnectionInfo(channel, activity.getConnectionInfoListener());
                }else{
                    System.out.println("没有连接上");
                }
                break;
            //当前设备的Wifi状态发生改变时会广播该信号(Wifi连接，断开)
            case WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION:
                System.out.println("WIFI_P2P_THIS_DEVICE_CHANGED_ACTION");
                break;
        }
    }

}
