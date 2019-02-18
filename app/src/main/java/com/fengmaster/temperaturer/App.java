package com.fengmaster.temperaturer;

import android.app.Application;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.fengmaster.temperaturer.bluetooth.TemperaturerBluetoothConnector;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by FengMaster on 18/12/24.
 * 全局应用类
 */
public class App extends Application {

    private static App instance;

    public static App getInstance(){
        return instance;
    }

    private static Context context;

    private TemperaturerBluetoothConnector temperaturerBluetoothConnector;

    private List<AppCompatActivity> activities=new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
        instance=this;
        temperaturerBluetoothConnector =TemperaturerBluetoothConnector.getInstance();
    }

    public static Context getContext() {
        return context;
    }

    public void addActivity_(AppCompatActivity appCompatActivity){
        if (!activities.contains(appCompatActivity)){
            activities.add(appCompatActivity);
        }
    }


    public void removeActivity_(AppCompatActivity appCompatActivity){
        if (activities.contains(appCompatActivity)){
            activities.remove(appCompatActivity);
        }
    }

    public void finish(){
        for (AppCompatActivity activity : activities) {
            activity.finish();
        }
    }
}
