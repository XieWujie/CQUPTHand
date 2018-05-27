package com.example.xiewujie.cqupthand.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.xiewujie.cqupthand.R;
import com.example.xiewujie.cqupthand.adapter.VPWeekAdapter;
import com.example.xiewujie.cqupthand.httpUtil.HttpGetUtil;
import com.example.xiewujie.cqupthand.listener.GetInputListener;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class LookPicActivity extends AppCompatActivity {
    private List<View> views = new ArrayList<>();
    private ViewPager page;
    Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look_pic);
        String[] array = getIntent().getStringArrayExtra("pics");
        Log.d("LookPicActivity",array.toString());
        page = (ViewPager)findViewById(R.id.look_pic_view_pager);
        postDate(array);
    }

    private void postDate(String[] addressList) {
        for (int i = 0; i < addressList.length; i++) {
            String address = addressList[i];
            HttpGetUtil.getInputStram(address, new GetInputListener() {
                @Override
                public void onInputStream(InputStream inputStream) {
                    final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            ImageView imageView = new ImageView(LookPicActivity.this);
                            imageView.setImageBitmap(bitmap);
                            views.add(imageView);
                        }
                    });
                }
            });
        }
        VPWeekAdapter adapter = new VPWeekAdapter(views);
        page.setAdapter(adapter);
    }
}
