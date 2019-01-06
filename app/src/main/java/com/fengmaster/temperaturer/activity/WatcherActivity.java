package com.fengmaster.temperaturer.activity;

import android.bluetooth.BluetoothGattService;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.databinding.DataBindingUtil;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.alibaba.fastjson.JSONObject;
import com.fengmaster.temperaturer.R;
import com.fengmaster.temperaturer.bluetooth.BluetoothHelper;
import com.fengmaster.temperaturer.bluetooth.base.BluetoothModel;
import com.fengmaster.temperaturer.databinding.ActivityWatcherBinding;
import com.fengmaster.temperaturer.entry.QueryResponse;
import com.fengmaster.temperaturer.entry.WatcherParms;
import com.fengmaster.temperaturer.event.BluetoothOriginalMessage;
import com.fengmaster.temperaturer.service.BluetoothLeService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class WatcherActivity extends AppCompatActivity {

    public static final String EXT_NAME="EXT_NAME";
    public static final String EXT_ADDRESS="EXT_ADDRESS";

    /**
     * 蓝牙名称
     */
    private String bluetoothName;
    /**
     * 蓝牙地址
     */
    private String bluetoothAddress;




    private BluetoothLeService bluetoothLeService;


    @BindView(R.id.et_address)
    public EditText edAddress;

    @BindView(R.id.bt_watcher_connect)
    public Button btConnect;

    @BindView(R.id.cb_watcher_auto_query)
    public CheckBox cbAutoQuery;

    @BindView(R.id.bt_watcher_query)
    public Button btQuery;

    @BindView(R.id.sp_watcher_query_interval)
    public Spinner spQueryInterval;

    private WatcherParms watcherParms=new WatcherParms();

    private ActivityWatcherBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=DataBindingUtil.setContentView(this,R.layout.activity_watcher);
        binding.setWatcherParms(watcherParms);
        ButterKnife.bind(this,binding.getRoot());
        initView();
        EventBus.getDefault().register(this);

        Intent intent = getIntent();
        bluetoothName=intent.getStringExtra(EXT_NAME);
        bluetoothAddress=intent.getStringExtra(EXT_ADDRESS);

        BluetoothModel model=new BluetoothModel();
        model.setDeviceName(bluetoothName);
        model.setAddress(bluetoothAddress);
        BluetoothHelper.getInstance().connect(model);

    }

    private void initView(){
        cbAutoQuery.setOnCheckedChangeListener((buttonView, isChecked) -> queryTypeChange());
        queryTypeChange();
    }

    private void queryTypeChange(){
        if (cbAutoQuery.isChecked()){
//        自动查询模式下,不会显示查询按钮
            btQuery.setVisibility(View.GONE);
            spQueryInterval.setVisibility(View.VISIBLE);
        }else {
            btQuery.setVisibility(View.VISIBLE);
            spQueryInterval.setVisibility(View.GONE);
        }

    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onQueryReceive(QueryResponse queryResponse){
        watcherParms.copy(queryResponse);
    }

    @OnClick(R.id.bt_watcher_connect)
    public void connect(View view){
        //点击连接按钮
        BluetoothHelper.getInstance().sendString("{\"Type\": \"Check\"}");


    }





}
