package com.example.xiewujie.cqupthand;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.xiewujie.cqupthand.httpUtil.HttpPostUtil;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_ADDRESS = "https://wx.idsbllp.cn/api/verify";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
    }

    private void logIn(){

    }
}
