package com.example.qjm3662.android5study.WifiDirect;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WpsInfo;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qjm3662.android5study.FileManager.FileManager;
import com.example.qjm3662.android5study.FileManager.FileUtils;
import com.example.qjm3662.android5study.MainActivity;
import com.example.qjm3662.android5study.R;
import com.example.qjm3662.android5study.Socket.Server;

import java.util.ArrayList;
import java.util.List;

public class MyWifiDirectActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private WifiP2pManager manager;
    private WifiP2pManager.Channel channel;
    private WifiDirectBroadCastReceiver receiver;
    private IntentFilter intentFilter;
    private WifiP2pManager.PeerListListener peerListListener;
    private WifiP2pManager.ConnectionInfoListener connectionInfoListener;
    private List<WifiP2pDevice> peers = new ArrayList<WifiP2pDevice>();
    private ListView peersListView;
    private PeersListAdapter adapter;
    private Button btn_search;
    private Button btn_createGroup;
    private Button btn_disConnect;
    private Button btn_sendFile;
    private String groupIp = null;
    private ProgressBar progressBar;
    private TextView tv_fileInfo;
    private Button btn_openFile;
    private String fileName = "";
    private String path = "";
    private int progress = 0;
    private Thread StartServerThread = null;
    private boolean IsGouper = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wifi_direct);
        initListener();
        init();
    }

    public WifiP2pManager.ConnectionInfoListener getConnectionInfoListener() {
        return connectionInfoListener;
    }
    public WifiP2pManager.PeerListListener getPeersListener(){
        return peerListListener;
    }

    private Server.ServerListener serverListener = new Server.ServerListener() {
        @Override
        public void FileInfoCallback(String fileName, String path) {
            MyWifiDirectActivity.this.fileName =fileName;
            MyWifiDirectActivity.this.path = path;
            handler.sendEmptyMessage(1);
        }

        @Override
        public void FileProgressCallback(int progress) {
            MyWifiDirectActivity.this.progress = progress;
            handler.sendEmptyMessage(0);
        }
    };
    private void initListener() {
        peerListListener = new WifiP2pManager.PeerListListener() {
            @Override
            public void onPeersAvailable(WifiP2pDeviceList peers) {
                MyWifiDirectActivity.this.peers.clear();
                MyWifiDirectActivity.this.peers.addAll(peers.getDeviceList());
                adapter.notifyDataSetChanged();
            }
        };
        connectionInfoListener = new WifiP2pManager.ConnectionInfoListener() {
            @Override
            public void onConnectionInfoAvailable(WifiP2pInfo info) {
                Toast.makeText(MyWifiDirectActivity.this, "连接成功", Toast.LENGTH_SHORT).show();
                IsGouper = info.isGroupOwner;
                System.out.println("WifiP2PInfo : " + info);
                System.out.println("Address : " + info.groupOwnerAddress);
                groupIp = String.valueOf(info.groupOwnerAddress);
                if(IsGouper){
                    if(StartServerThread == null){
                        StartServer(IsGouper);
                        System.out.println("创建了一个线程");
                    }
                    if(!StartServerThread.isAlive()){
                        StartServer(IsGouper);
                        System.out.println("又创建了一个线程");
                    }
                }
            }


        };
    }

    private void StartServer(final boolean IsGroup) {
        System.out.println("StartServer");
        StartServerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    new Server(serverListener, IsGroup);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        StartServerThread.start();
    }

    private void init() {
        manager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        channel = manager.initialize(this, getMainLooper(), null);
        receiver = new WifiDirectBroadCastReceiver(manager, channel, MyWifiDirectActivity.this, peerListListener);
        intentFilter = new IntentFilter();
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
        registerReceiver(receiver, intentFilter);
        btn_search = (Button) findViewById(R.id.btn_search);
        btn_search.setOnClickListener(this);
        btn_createGroup = (Button) findViewById(R.id.btn_createGroup);
        btn_createGroup.setOnClickListener(this);
        btn_disConnect = (Button) findViewById(R.id.btn_disConnect);
        btn_disConnect.setOnClickListener(this);
        btn_sendFile = (Button) findViewById(R.id.btn_sendFile);
        btn_sendFile.setOnClickListener(this);

        peersListView = (ListView) findViewById(R.id.list);
        adapter = new PeersListAdapter(this, peers);
        peersListView.setAdapter(adapter);
        peersListView.setOnItemClickListener(this);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        tv_fileInfo = (TextView) findViewById(R.id.tv_fileInfo);
        btn_openFile = (Button) findViewById(R.id.btn_openFile);
        btn_openFile.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:         //文件传输进度
                    progressBar.setProgress(progress);
                    break;
                case 1:
                    tv_fileInfo.append("FileName:" + fileName + "\n");
                    tv_fileInfo.append("Path:" + path + "\n");
                    break;
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_search:
                Discover();
                break;
            case R.id.btn_createGroup:
                CreateGroup();
                break;
            case R.id.btn_disConnect:
                DisConnect();
                break;
            case R.id.btn_sendFile:
                SendFile();
                break;
            case R.id.btn_openFile:
                FileUtils.OpenFile(this, path, fileName);
                break;
        }
    }

    private void SendFile() {
        try{
            Intent intent = new Intent(this, FileManager.class);
            intent.putExtra(MainActivity.FILE_SELECT, MainActivity.FILE_SELECT_CODE);
            startActivityForResult(intent, 6);
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        }catch (Exception e){
            System.out.println("打开错误 : " + e.toString());
        }
    }

    private void DisConnect() {
        peers.clear();
        adapter.notifyDataSetChanged();
        manager.removeGroup(channel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(MyWifiDirectActivity.this, "解散群组", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int reason) {

            }
        });
    }

    private void CreateGroup() {
        manager.createGroup(channel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(MyWifiDirectActivity.this, "创建群组成功", Toast.LENGTH_SHORT).show();
                IsGouper = true;
            }

            @Override
            public void onFailure(int reason) {

            }
        });
    }

    private void Discover() {
        manager.discoverPeers(channel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                System.out.println("Success");
            }
            @Override
            public void onFailure(int reason) {
                System.out.println("Fail");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case MainActivity.requestCode_selectFile:
                if (data != null) {
                    System.out.println("Path : " +data.getStringExtra(MainActivity.PATH));
                    FileSendAsycn fileSendAsycn;
                    if(IsGouper){//组长发文件
                        fileSendAsycn = new FileSendAsycn(IsGouper, Server.getCurrentClient());
                    }else{
                        fileSendAsycn = new FileSendAsycn(IsGouper);
                    }
                    groupIp = groupIp.substring(1, groupIp.length());
                    System.out.println("Ip : " + groupIp);
                    try{
                        fileSendAsycn.execute(data.getStringExtra(MainActivity.PATH), groupIp);
                    }catch(Exception e){
                        System.out.println("传送文件错误 ： " + e.toString());
                    }
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        WifiP2pConfig config = new WifiP2pConfig();
        config.deviceAddress = peers.get(position).deviceAddress;
        config.wps.setup = WpsInfo.PBC;
        manager.connect(channel,config, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
            }
            @Override
            public void onFailure(int reason) {
                // ...
            }
        });
    }
}
