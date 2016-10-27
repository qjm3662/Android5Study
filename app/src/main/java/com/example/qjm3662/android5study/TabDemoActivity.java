package com.example.qjm3662.android5study;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TabHost;

public class TabDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_demo);

        //step1 获得TabHost对象，并进行初始化setup()
        TabHost tabs = (TabHost) findViewById(R.id.tab_host);
        tabs.setup();

        TabHost.TabSpec spec = tabs.newTabSpec("Tag1");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Tag1");
        tabs.addTab(spec);

        spec = tabs.newTabSpec("Tag2");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Tag2");
        tabs.addTab(spec);

        spec = tabs.newTabSpec("Tag3");
        spec.setContent(R.id.tab3);
        spec.setIndicator("Tag3");
        tabs.addTab(spec);

        tabs.setCurrentTab(0);
    }
}
