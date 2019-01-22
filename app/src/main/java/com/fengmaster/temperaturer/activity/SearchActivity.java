package com.fengmaster.temperaturer.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.fengmaster.temperaturer.R;
import com.fengmaster.temperaturer.adapter.BluetoothSearchAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 搜索蓝牙页面
 */
public class SearchActivity extends AppCompatActivity {

    @BindView(R.id.rv_search_bluetooth_list)
    public RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        initView();
        setTitle("选择蓝牙设备");
    }


    private void initView(){
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(new BluetoothSearchAdapter(this));
    }
}
