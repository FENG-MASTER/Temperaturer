package com.fengmaster.temperaturer.adapter;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.fengmaster.temperaturer.App;
import com.fengmaster.temperaturer.R;
import com.fengmaster.temperaturer.activity.WatcherActivity;
import com.fengmaster.temperaturer.bluetooth.BluetoothHelper;
import com.fengmaster.temperaturer.bluetooth.base.BluetoothModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 显示搜索蓝牙设备的适配器
 * Created by FengMaster on 18/12/27.
 */
public class BluetoothSearchAdapter extends RecyclerView.Adapter<BluetoothSearchAdapter.Holder> {

    private Context context;
    private BluetoothHelper helper;

    private List<BluetoothModel> bluetoothModels=new ArrayList<>();

    //搜索蓝牙回调
    private ScanCallback scanCallback=new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            super.onScanResult(callbackType, result);
            BluetoothModel model = toBluetoothModel(result);
            if (!bluetoothModels.contains(model)){
                bluetoothModels.add(model);
                notifyDataSetChanged();
            }

        }

        @Override
        public void onBatchScanResults(List<ScanResult> results) {
            super.onBatchScanResults(results);
            List<BluetoothModel> resultList = Stream.of(results).filterNot(value -> bluetoothModels.contains(value)).map(result -> toBluetoothModel(result)).collect(Collectors.toList());
            if (resultList==null||resultList.isEmpty()){
                return;
            }
            bluetoothModels.addAll(resultList);
            notifyDataSetChanged();
        }

    };


    public BluetoothSearchAdapter(Context context) {
        this.context = context;
        helper=BluetoothHelper.getInstance();
        helper.startScanBluetooth(scanCallback);
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_adapter_search_bluetooth, parent, false);
        return new Holder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        BluetoothModel model = bluetoothModels.get(position);
        holder.setDevice(model);
        holder.address.setText(model.getAddress());
        holder.name.setText(model.getDeviceName());
    }

    @Override
    public int getItemCount() {
        return bluetoothModels.size();
    }

    public static class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_item_search_bluetooth_address)
        public TextView address;

        @BindView(R.id.tv_item_search_bluetooth_name)
        public TextView name;

        private BluetoothModel device;

        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }


        public BluetoothModel getDevice() {
            return device;
        }

        public void setDevice(BluetoothModel device) {
            this.device = device;
        }

        @Override
        public void onClick(View v) {
            Intent intent=new Intent(App.getContext(),WatcherActivity.class);
            intent.putExtra(WatcherActivity.EXT_NAME,device.getDeviceName());
            intent.putExtra(WatcherActivity.EXT_ADDRESS,device.getAddress());
            App.getContext().startActivity(intent);
        }
    }


    private BluetoothModel toBluetoothModel(ScanResult result){

        BluetoothModel model=new BluetoothModel();
        if (result.getDevice().getAddress()!=null){
            model.setAddress(result.getDevice().getAddress());
        }
        if (result.getDevice().getName()!=null){
            model.setDeviceName(result.getDevice().getName());
        }
        return model;
    }
}
