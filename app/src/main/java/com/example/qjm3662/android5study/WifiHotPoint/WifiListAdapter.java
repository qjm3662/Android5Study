package com.example.qjm3662.android5study.WifiHotPoint;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.qjm3662.android5study.R;
import com.example.qjm3662.android5study.WifiDirect.PeersListAdapter;

import java.util.List;

/**
 * Created by tanshunwang on 2016/10/28 0028.
 */

public class WifiListAdapter extends BaseAdapter {
    private List<ScanResult> wifiList;
    private WifiInfo wifiInfo;
    private ViewHolder viewHolder = null;
    private LayoutInflater inflater;

    public String getIp() {
        return ip;
    }

    private String ip;


    public WifiListAdapter(Context context, List<ScanResult> wifiList, WifiInfo wifiInfo) {
        this.wifiList = wifiList;
        this.wifiInfo = wifiInfo;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    class ViewHolder {
        private TextView tv_name;
        private TextView tv_information;
    }

    @Override
    public int getCount() {
        if (wifiList == null) {
            return 0;
        }
        System.out.println("get Count : " + wifiList.size());
        return wifiList.size();
    }

    @Override
    public Object getItem(int position) {
        System.out.println("getItem : " + position);
        return wifiList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public WifiInfo getWifiInfo() {
        return wifiInfo;
    }

    public void setWifiInfo(WifiInfo wifiInfo) {
        this.wifiInfo = wifiInfo;
    }

    public List<ScanResult> getWifiList() {
        return wifiList;
    }

    public void setWifiList(List<ScanResult> wifiList) {
        this.wifiList = wifiList;
    }

    @Override

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.row_deceive, null);
            viewHolder.tv_name = (TextView) convertView.findViewById(R.id.deceive_name);
            viewHolder.tv_information = (TextView) convertView.findViewById(R.id.deceive_details);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        System.out.println("get View:" + position);
        ScanResult scanResult = wifiList.get(position);
        if(scanResult.SSID.equals("sdfs")){

        }
        viewHolder.tv_name.setText(scanResult.SSID);
        viewHolder.tv_information.setText(scanResult.BSSID);

        return convertView;
    }


}
