package com.example.qjm3662.android5study;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class AutoCompleteTextActivity extends AppCompatActivity {

    private AutoCompleteTextView autoCompleteTextView;
    private static final String[] autotext = new String[]{"Robbin", "Robbin you", "Robin you are", "Robin you are my love"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_complete_text);
        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.auto_complete_text_view);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, autotext);
        autoCompleteTextView.setAdapter(adapter);

    }
}
