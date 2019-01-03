package com.fengmaster.temperaturer.activity;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.alibaba.fastjson.JSONObject;
import com.fengmaster.temperaturer.R;
import com.fengmaster.temperaturer.databinding.ActivityWatcherBinding;
import com.fengmaster.temperaturer.entry.QueryResponse;
import com.fengmaster.temperaturer.entry.WatcherParms;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;


public class WatcherActivity extends AppCompatActivity {

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
        QueryResponse queryResponse=JSONObject.parseObject("{\n" +
                "        \"SN\":   999999,\n" +
                "        \"S1\":   {\n" +
                "                \"T\":    20.299999,\n" +
                "                \"RH\":   75\n" +
                "        },\n" +
                "        \"S2\":   {\n" +
                "                \"T\":    21.440001,\n" +
                "                \"RH\":   72\n" +
                "        },\n" +
                "        \"S3\":   {\n" +
                "                \"T\":    21.059999,\n" +
                "                \"RH\":   72\n" +
                "        },\n" +
                "        \"K1\":   {\n" +
                "                \"Relation\":     \"T1\",\n" +
                "                \"Min\":  1,\n" +
                "                \"Max\":  99,\n" +
                "                \"Mode\": \"AUTO\",\n" +
                "                \"State\":        \"Close\"\n" +
                "        },\n" +
                "        \"K2\":   {\n" +
                "                \"Relation\":     \"T2\",\n" +
                "                \"Min\":  1,\n" +
                "                \"Max\":  99,\n" +
                "                \"Mode\": \"AUTO\",\n" +
                "                \"State\":        \"Close\"\n" +
                "        },\n" +
                "        \"K3\":   {\n" +
                "                \"Relation\":     \"T3\",\n" +
                "                \"Min\":  1,\n" +
                "                \"Max\":  99,\n" +
                "                \"Mode\": \"AUTO\",\n" +
                "                \"State\":        \"Close\"\n" +
                "        },\n" +
                "        \"K4\":   {\n" +
                "                \"Relation\":     \"RH1\",\n" +
                "                \"Min\":  1,\n" +
                "                \"Max\":  99,\n" +
                "                \"Mode\": \"AUTO\",\n" +
                "                \"State\":        \"Close\"\n" +
                "        },\n" +
                "        \"T1\":   {\n" +
                "                \"A\":    1,\n" +
                "                \"B\":    0\n" +
                "        },\n" +
                "        \"T2\":   {\n" +
                "                \"A\":    1,\n" +
                "                \"B\":    0\n" +
                "        },\n" +
                "        \"T3\":   {\n" +
                "                \"A\":    1,\n" +
                "                \"B\":    0\n" +
                "        },\n" +
                "        \"RH1\":  {\n" +
                "                \"A\":    1,\n" +
                "                \"B\":    0\n" +
                "        },\n" +
                "        \"RH2\":  {\n" +
                "                \"A\":    1,\n" +
                "                \"B\":    0\n" +
                "        },\n" +
                "        \"RH3\":  {\n" +
                "                \"A\":    1,\n" +
                "                \"B\":    0\n" +
                "        }\n" +
                "}\n",QueryResponse.class);

        EventBus.getDefault().post(queryResponse);

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


}
