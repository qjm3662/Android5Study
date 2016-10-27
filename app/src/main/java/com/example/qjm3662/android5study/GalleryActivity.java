package com.example.qjm3662.android5study;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

public class GalleryActivity extends AppCompatActivity {

    private Gallery gallery;
    private ImageSwitcher imageSwitcher;
    private int[] resids = new int[]{
      R.drawable.img1,R.drawable.img2,R.drawable.img3,R.drawable.img4,R.drawable.img5,R.drawable.img6
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        initView();
    }

    private void initView() {
        gallery = (Gallery) findViewById(R.id.gallery);
        imageSwitcher = (ImageSwitcher) findViewById(R.id.switcher);

        ImageAdapter adapter = new ImageAdapter(this);
        gallery.setAdapter(adapter);
        gallery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                imageSwitcher.setImageResource(resids[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //设置ImageSwitcher组件的工厂对象
        imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            //ImageSwitcher 用这个方法来创建一个View去显示图片
            @Override
            public View makeView() {
                ImageView imageView = new ImageView(GalleryActivity.this);
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imageView.setLayoutParams(new ImageSwitcher.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

                return imageView;
            }
        });

        //设置ImageSwitcher组件显示图像时的动画效果
        imageSwitcher.setInAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
        imageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));

    }


    class ImageAdapter extends BaseAdapter{

        private Context context;

        public ImageAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return resids.length;
        }

        @Override
        public Object getItem(int position) {
            return resids[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView = new ImageView(context);
            imageView.setImageResource(resids[position]);
            imageView.setLayoutParams(new Gallery.LayoutParams(100, 100));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return imageView;
        }
    }
}
