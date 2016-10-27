package com.example.qjm3662.android5study;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.SeekBar;

public class BitmapDemoActivity extends AppCompatActivity {

    private ImageView imageView;
    private Bitmap myBitmap, newBitmap;
    int bmWith, bmHeight;
    private SeekBar seekBar;
    float rotAngle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap_demo);

        imageView = (ImageView) findViewById(R.id.imageView);
        myBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon1);
        bmWith = myBitmap.getWidth();
        bmHeight = myBitmap.getHeight();
        //实例化Matrix
//        Matrix matrix = new Matrix();
        //设定Matrix属性x， y的缩放比例为1.5
//        matrix.postScale(1.5F, 1.5F);
        //顺时针旋转45度
//        matrix.postRotate(45.0F);
//        newBitmap = Bitmap.createBitmap(myBitmap, 0, 0, bmWith, bmHeight, matrix, true);

        seekBar = (SeekBar) findViewById(R.id.seekBar);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Matrix m = new Matrix();
                m.postRotate((float)progress*3.6F);
                newBitmap = Bitmap.createBitmap(myBitmap, 0, 0, bmWith, bmHeight, m, true);
                imageView.setImageBitmap(newBitmap);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
