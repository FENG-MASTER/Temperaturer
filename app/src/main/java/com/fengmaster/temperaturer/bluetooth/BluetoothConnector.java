package com.fengmaster.temperaturer.bluetooth;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;

import com.alibaba.fastjson.JSONObject;
import com.fengmaster.temperaturer.bluetooth.itf.ICharacteristicChangeListener;
import com.fengmaster.temperaturer.entry.QueryResponse;

import org.greenrobot.eventbus.EventBus;

/**
 * 和蓝牙设备交互的接口
 * Created by FengMaster on 18/12/25.
 */
public class BluetoothConnector implements ICharacteristicChangeListener {

    private BluetoothHelper bluetoothHelper;

    private static final String O_QUERY_PARMS_MESSAGE="#";

    public BluetoothConnector() {
        bluetoothHelper=BluetoothHelper.getInstance();
        bluetoothHelper.addCharacteristicChangeListener(this);
    }

    /**
     * 查询所有参数
     * @return 是否发送成功
     */
    public boolean queryParms(){
        //发送查询报文
        return bluetoothHelper.sendString(O_QUERY_PARMS_MESSAGE);
    }


    @Override
    public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
        //处理报文


        //发送查询返回信息
        byte[] value = characteristic.getValue();
        QueryResponse queryResponse=JSONObject.parseObject(new String(value),QueryResponse.class);
        EventBus.getDefault().post(queryResponse);

    }
}
