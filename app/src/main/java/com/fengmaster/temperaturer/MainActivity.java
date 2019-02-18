package com.fengmaster.temperaturer;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;


import com.fengmaster.temperaturer.activity.BaseActivity;
import com.fengmaster.temperaturer.activity.SearchActivity;
import com.fengmaster.temperaturer.bluetooth.BluetoothHelper;

import java.util.ArrayList;
import java.util.List;

import static android.support.v7.app.AppCompatDelegate.MODE_NIGHT_YES;

public class MainActivity extends BaseActivity {

    // 要申请的权限
     private String[] permissions = {Manifest.permission.BLUETOOTH,Manifest.permission.BLUETOOTH_ADMIN,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boolean b = checkCallingPermission("android.permission.BLUETOOTH") == PackageManager.PERMISSION_GRANTED;
        boolean b1 = checkCallingPermission("android.permission.BLUETOOTH_ADMIN") == PackageManager.PERMISSION_GRANTED;
        boolean b2 = checkCallingPermission("android.permission.ACCESS_FINE_LOCATION") == PackageManager.PERMISSION_GRANTED;
        boolean b3 = checkCallingPermission("android.permission.ACCESS_COARSE_LOCATION") == PackageManager.PERMISSION_GRANTED;


        List<String> needPermissions=new ArrayList<>();

        for (String permission : permissions) {
            if (checkCallingPermission(permission) != PackageManager.PERMISSION_GRANTED){
                needPermissions.add(permission);
            }
        }
        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES);

        if (!needPermissions.isEmpty()){
            ActivityCompat.requestPermissions(this,permissions,666);
        }else {

            if (!BluetoothAdapter.getDefaultAdapter().isEnabled()){
                BluetoothAdapter.getDefaultAdapter().enable();
            }

            startActivity(new Intent(this,SearchActivity.class));
            BluetoothHelper.getInstance().init();
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==666){

            if (permissions.length!=0){

                if (!BluetoothAdapter.getDefaultAdapter().isEnabled()){
                    BluetoothAdapter.getDefaultAdapter().enable();
                }

                startActivity(new Intent(this,SearchActivity.class));

                BluetoothHelper.getInstance().init();
            }


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
