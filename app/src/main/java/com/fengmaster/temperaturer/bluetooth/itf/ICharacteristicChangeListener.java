package com.fengmaster.temperaturer.bluetooth.itf;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;

/**
 * 特征值改变接口,即蓝牙设备返回值接口
 * Created by FengMaster on 18/12/25.
 */
public interface ICharacteristicChangeListener {

    void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic);

}
