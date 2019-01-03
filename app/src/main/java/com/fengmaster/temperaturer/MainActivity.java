package com.fengmaster.temperaturer;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;


import com.fengmaster.temperaturer.activity.SearchActivity;

import static android.support.v7.app.AppCompatDelegate.MODE_NIGHT_YES;

public class MainActivity extends AppCompatActivity {

    // 要申请的权限
     private String[] permissions = {Manifest.permission.BLUETOOTH,Manifest.permission.BLUETOOTH_ADMIN};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boolean b = checkCallingPermission("android.permission.BLUETOOTH") == PackageManager.PERMISSION_GRANTED;
        boolean b1 = checkCallingPermission("android.permission.BLUETOOTH_ADMIN") == PackageManager.PERMISSION_GRANTED;

        if (!b||!b1){
            ActivityCompat.requestPermissions(this,permissions,666);
        }else {
            startActivity(new Intent(this,SearchActivity.class));
        }

        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==666){

            startActivity(new Intent(this,SearchActivity.class));


        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==666){

            startActivity(new Intent(this,SearchActivity.class));


        }

    }
}
