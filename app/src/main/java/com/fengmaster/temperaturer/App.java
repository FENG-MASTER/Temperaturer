package com.fengmaster.temperaturer;

import android.app.Application;
import android.content.Context;

import com.fengmaster.temperaturer.bluetooth.TemperaturerBluetoothConnector;

/**
 * Created by FengMaster on 18/12/24.
 * 全局应用类
 */
public class App extends Application {

    private static Context context;

    private TemperaturerBluetoothConnector temperaturerBluetoothConnector;

    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
        temperaturerBluetoothConnector =TemperaturerBluetoothConnector.getInstance();
    }

    public static Context getContext() {
        return context;
    }
}
