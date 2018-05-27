package com.example.xiewujie.cqupthand.tool;

import android.app.Application;
import android.content.Context;

public class MyAppliction extends Application {
    static Context context;
    @Override
    public void onCreate() {
        context = getApplicationContext();
    }

    public static Context getContext(){
        return context;
    }
}
