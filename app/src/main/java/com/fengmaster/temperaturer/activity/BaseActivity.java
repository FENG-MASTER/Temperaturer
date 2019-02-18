package com.fengmaster.temperaturer.activity;

import android.support.v7.app.AppCompatActivity;

import com.fengmaster.temperaturer.App;

/**
 * Created by FengMaster on 19/01/28.
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onStart() {
        super.onStart();
        App.getInstance().addActivity_(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.getInstance().removeActivity_(this);
    }
}
