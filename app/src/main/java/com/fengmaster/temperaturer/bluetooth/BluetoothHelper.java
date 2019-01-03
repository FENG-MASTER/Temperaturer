package com.fengmaster.temperaturer.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import com.fengmaster.temperaturer.App;
import com.fengmaster.temperaturer.bluetooth.base.BluetoothModel;
import com.fengmaster.temperaturer.bluetooth.itf.ICharacteristicChangeListener;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by FengMaster on 18/12/24.
 */
public class BluetoothHelper extends BluetoothGattCallback {

    private static BluetoothHelper instance=new BluetoothHelper();

    public static BluetoothHelper getInstance(){
        return instance;
    }

    private Context context;

    //蓝牙管理器
    private BluetoothManager bluetoothManager;
    //蓝牙适配器
    private BluetoothAdapter bluetoothAdapter;
    //蓝牙搜索器
    private BluetoothLeScanner bluetoothLeScanner;

    //蓝牙连接后的对象
    private BluetoothGatt bluetoothGatt;
    //当前连接的设备信息
    private BluetoothModel bluetoothModel;

    //特征值-写
    private BluetoothGattCharacteristic writeCharacteristic;

    //接收返回信息监听者
    private List<ICharacteristicChangeListener> characteristicChangeListeners=new ArrayList<>();


    private BluetoothHelper() {
        context= App.getContext();
        initBluetooth();
    }

    public void initBluetooth(){

        //获取蓝牙管理器
        bluetoothManager= (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
        if (bluetoothManager==null){
            Log.e(getClass().getSimpleName(),"蓝牙服务获取失败");
            return;
        }

        bluetoothAdapter = bluetoothManager.getAdapter();
        if (bluetoothAdapter==null){
            Log.e(getClass().getSimpleName(),"蓝牙服务适配器获取失败");
            return;
        }

        bluetoothLeScanner = bluetoothAdapter.getBluetoothLeScanner();
        if (bluetoothLeScanner==null){
            Log.e(getClass().getSimpleName(),"蓝牙搜索器获取失败");
            return;
        }

    }


    public void startScanBluetooth(ScanCallback callback){
        if (bluetoothManager==null||bluetoothAdapter==null||bluetoothLeScanner==null){
            return;
        }

        ScanSettings.Builder builder=new ScanSettings.Builder();
        builder.setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY);
        bluetoothLeScanner.startScan(null,builder.build(),callback);

    }

    public void stopScanBluetooth(ScanCallback callback){
        if (bluetoothManager==null||bluetoothAdapter==null||bluetoothLeScanner==null){
            return;
        }

        bluetoothLeScanner.stopScan(callback);
    }


    public boolean connect(BluetoothModel model){
        if (model==null){
            return false;
        }

        BluetoothDevice remoteDevice = bluetoothAdapter.getRemoteDevice(model.getAddress());
        if(remoteDevice==null){
            Log.e(getClass().getSimpleName(),"找不到蓝牙设备");
            return false;
        }

        bluetoothGatt = remoteDevice.connectGatt(context, false, this);
        if (bluetoothGatt==null){
            Log.e(getClass().getSimpleName(),"连接蓝牙设备失败");
            return false;
        }

        bluetoothModel=model;
        //获取特征值用于通讯
        writeCharacteristic=getBluetoothGattCharacteristic(BluetoothConst.UUID);
        return true;

    }

    public BluetoothGattCharacteristic getBluetoothGattCharacteristic(String uuid){
        if (bluetoothGatt==null){
            return null;
        }

        List<BluetoothGattService> services = bluetoothGatt.getServices();
        for (BluetoothGattService service : services) {
            List<BluetoothGattCharacteristic> characteristics = service.getCharacteristics();
            for (BluetoothGattCharacteristic characteristic : characteristics) {
                if (characteristic.getUuid().toString().equals(uuid)){
                    return characteristic;
                }
            }
        }

        return null;

    }



    public boolean sendBytes(byte[] bytes){
        if (bluetoothGatt==null||writeCharacteristic==null){
            return false;
        }
        writeCharacteristic.setValue(bytes);
        boolean b = bluetoothGatt.writeCharacteristic(writeCharacteristic);
        int i=0;
        while (!b){
            i++;
            b=bluetoothGatt.writeCharacteristic(writeCharacteristic);
            if (i>5){
                break;
            }
        }

        return b;

    }

    /**
     * 发送默认编码格式字符串到蓝牙设备
     * @param str
     * @return
     */
    public boolean sendString(String str){
        return sendString(str,Charset.forName("ASCII"));
    }


    /**
     * 发送指定编码格式字符串到蓝牙设备
     * @param str
     * @param charset 指定编码格式
     * @return
     */
    public boolean sendString(String str,Charset charset){
        byte[] bytes = new String(str.getBytes(Charset.defaultCharset()), charset).getBytes(charset);
        return sendBytes(bytes);
    }

    @Override
    public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
        super.onConnectionStateChange(gatt, status, newState);
    }

    @Override
    public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
        super.onCharacteristicChanged(gatt, characteristic);
        //特征值变化,即蓝牙设备返回信息

        //通知监听者,有返回数据
        for (ICharacteristicChangeListener listener : characteristicChangeListeners) {
            listener.onCharacteristicChanged(gatt,characteristic);
        }
    }

    public void addCharacteristicChangeListener(ICharacteristicChangeListener listener){
        characteristicChangeListeners.add(listener);
    }

    public void removeCharacteristicChangeListener(ICharacteristicChangeListener listener){
        characteristicChangeListeners.remove(listener);
    }

}
