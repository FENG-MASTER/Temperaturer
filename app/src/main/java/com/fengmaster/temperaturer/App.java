package com.fengmaster.temperaturer;

import android.app.Application;
import android.content.Context;

/**
 * Created by FengMaster on 18/12/24.
 * 全局应用类
 */
public class App extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
