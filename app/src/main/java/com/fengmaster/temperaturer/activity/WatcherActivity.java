package com.fengmaster.temperaturer.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.fengmaster.temperaturer.R;
import com.fengmaster.temperaturer.bluetooth.BluetoothHelper;
import com.fengmaster.temperaturer.bluetooth.TemperaturerBluetoothConnector;
import com.fengmaster.temperaturer.bluetooth.base.BluetoothModel;
import com.fengmaster.temperaturer.databinding.ActivityWatcherBinding;
import com.fengmaster.temperaturer.entry.QueryResponse;
import com.fengmaster.temperaturer.entry.SetParmsRequest;
import com.fengmaster.temperaturer.entry.WatcherParms;
import com.fengmaster.temperaturer.util.ArrayUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;


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



    @BindView(R.id.et_address)
    public EditText edAddress;

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

    @OnClick(R.id.bt_watcher_query)
    public void queryParms(View view){
        //点击查询按钮
        TemperaturerBluetoothConnector.getInstance().queryParms();
    }

    @OnClick(R.id.bt_watcher_send)
    public void setParms(View view){
        //点击发送按钮,即设置参数
        TemperaturerBluetoothConnector.getInstance().setParms(new SetParmsRequest(watcherParms));
    }

    @OnItemSelected(R.id.sp_watcher_k1_trigger_type)
    public void K1TriggerTypeChange(View view,int i){
        watcherParms.getK1().setMode(ArrayUtil.getString(R.array.trigger_type_val,i));
    }

    @OnItemSelected(R.id.sp_watcher_k2_trigger_type)
    public void K2TriggerTypeChange(View view,int i){
        watcherParms.getK2().setMode(ArrayUtil.getString(R.array.trigger_type_val,i));
    }

    @OnItemSelected(R.id.sp_watcher_k3_trigger_type)
    public void K3TriggerTypeChange(View view,int i){
        watcherParms.getK3().setMode(ArrayUtil.getString(R.array.trigger_type_val,i));
    }

    @OnItemSelected(R.id.sp_watcher_k4_trigger_type)
    public void K4TriggerTypeChange(View view,int i){
        watcherParms.getK4().setMode(ArrayUtil.getString(R.array.trigger_type_val,i));
    }

    @OnItemSelected(R.id.sp_watcher_k1_trigger)
    public void K1TriggerChange(View view,int i){
        watcherParms.getK1().setRelation(ArrayUtil.getString(R.array.kList,i));
    }

    @OnItemSelected(R.id.sp_watcher_k2_trigger)
    public void K2TriggerChange(View view,int i){
        watcherParms.getK2().setRelation(ArrayUtil.getString(R.array.kList,i));
    }

    @OnItemSelected(R.id.sp_watcher_k3_trigger)
    public void K3TriggerChange(View view,int i){
        watcherParms.getK3().setRelation(ArrayUtil.getString(R.array.kList,i));
    }

    @OnItemSelected(R.id.sp_watcher_k4_trigger)
    public void K4TriggerChange(View view,int i){
        watcherParms.getK4().setRelation(ArrayUtil.getString(R.array.kList,i));
    }
}
