package com.example.qjm3662.android5study.WifiDirect;

import android.app.ListFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.qjm3662.android5study.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qjm3662 on 2016/10/25 0025.
 */

public class DeviceListFragment extends ListFragment implements WifiP2pManager.PeerListListener{
    private List<WifiP2pDevice> peers = new ArrayList<WifiP2pDevice>();
    private ProgressDialog progressDialog = null;
    private View myContentView = null;
    private WifiP2pDevice device;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.setListAdapter(new WifiPeerListAdapter(getActivity(), R.layout.row_deceive, peers));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myContentView = inflater.inflate(R.layout.deceive_list, null);
        return myContentView;
    }

    public WifiP2pDevice getDeceive(){
        return device;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        WifiP2pDevice device = (WifiP2pDevice) getListAdapter().getItem(position);
        ((DeviceActionListener)getActivity()).showDetails(device);
    }

    private String getDeceiveStatus(int status) {
        Log.d("STATE", "Peer status : " + status);
        switch (status){
            case WifiP2pDevice.AVAILABLE:
                return "Available";
            case WifiP2pDevice.CONNECTED:
                return "Connected";
            case WifiP2pDevice.FAILED:
                return "Failed";
            case WifiP2pDevice.UNAVAILABLE:
                return "Unavailable";
            default:
                return "Unknown";
        }
    }

    private class WifiPeerListAdapter extends ArrayAdapter<WifiP2pDevice>{

        private List<WifiP2pDevice> items;
        public WifiPeerListAdapter(Context context, int resource, List<WifiP2pDevice> objects) {
            super(context, resource, objects);
            items = objects;
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if(v != null){
                LayoutInflater vi = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.row_deceive, null);
                WifiP2pDevice device = items.get(position);
                if(device != null){
                    TextView top = (TextView) v.findViewById(R.id.deceive_name);
                    TextView bottom = (TextView) v.findViewById(R.id.deceive_details);
                    if(top != null){
                        top.setText(device.deviceName);
                    }
                    if(bottom != null){
                        bottom.setText(getDeceiveStatus(device.status));
                    }
                }
            }
            return v;
        }
    }

    public void updateThisDeceive(WifiP2pDevice device){
        this.device = device;

    }

    //搜索结束以后触发
    @Override
    public void onPeersAvailable(WifiP2pDeviceList peers) {

    }


    public interface DeviceActionListener{
        void showDetails(WifiP2pDevice device);

        void cancelConnection();

        void connect(WifiP2pConfig config);

        void disConnect();
    }
}
