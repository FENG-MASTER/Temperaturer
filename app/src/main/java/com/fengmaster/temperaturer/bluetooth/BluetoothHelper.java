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
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.util.Log;

import com.fengmaster.temperaturer.App;
import com.fengmaster.temperaturer.bluetooth.base.BluetoothModel;
import com.fengmaster.temperaturer.bluetooth.itf.ICharacteristicChangeListener;
import com.fengmaster.temperaturer.event.BluetoothOriginalMessage;
import com.fengmaster.temperaturer.service.BluetoothLeService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.BIND_AUTO_CREATE;


/**
 * Created by FengMaster on 18/12/24.
 */
public class BluetoothHelper extends BluetoothGattCallback {

    private static BluetoothHelper instance=new BluetoothHelper();

    public static BluetoothHelper getInstance(){
        return instance;
    }


    private BluetoothLeService bluetoothLeService;

    private Context context;


    public static String HEART_RATE_MEASUREMENT = "0000ffe1-0000-1000-8000-00805f9b34fb";
    //蓝牙管理器
    private BluetoothManager bluetoothManager;
    //蓝牙适配器
    private BluetoothAdapter bluetoothAdapter;
    //蓝牙搜索器
    private BluetoothLeScanner bluetoothLeScanner;

    //当前连接的设备信息
    private BluetoothModel bluetoothModel;


    /**
     * 蓝牙连接状态
     */
    private String state=STATE_DISCONNECT;

    public static final String STATE_CONNECT="connected";
    public static final String STATE_DISCONNECT="disconnected";


    //特征值-写
    private BluetoothGattCharacteristic writeCharacteristic;

    //接收返回信息监听者
    private List<ICharacteristicChangeListener> characteristicChangeListeners=new ArrayList<>();


    BluetoothHelper() {
        EventBus.getDefault().register(this);
    }

    public void init(){
        context= App.getContext();
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

        bluetoothLeScanner.startScan(callback);

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

        Intent gattServiceIntent=new Intent(context,BluetoothLeService.class);
        context.bindService(gattServiceIntent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                bluetoothLeService= ((BluetoothLeService.LocalBinder)service).getService();
                if (!bluetoothLeService.initialize())
                {

                }
                // 根据蓝牙地址，连接设备
                bluetoothLeService.connect(bluetoothModel.getAddress());
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                bluetoothLeService=null;
            }
        },BIND_AUTO_CREATE);


        bluetoothModel=model;

        return true;

    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void bluetoothMsg(BluetoothOriginalMessage originalMessage){
        switch (originalMessage.getMessage()){
            case BluetoothLeService.ACTION_GATT_CONNECTED:
                state=STATE_CONNECT;
                break;
            case BluetoothLeService.ACTION_GATT_DISCONNECTED:
                state=STATE_DISCONNECT;
                break;

            case BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED:
                //找到GATT服务器蓝牙设备
                if (bluetoothLeService!=null){
                    List<BluetoothGattService> supportedGattServices = bluetoothLeService.getSupportedGattServices();

                    for (BluetoothGattService gattService : supportedGattServices) {
                        List<BluetoothGattCharacteristic> characteristics = gattService.getCharacteristics();
                        for (BluetoothGattCharacteristic characteristic : characteristics) {
                            if (characteristic.getUuid().toString().equals(HEART_RATE_MEASUREMENT)){
                                //接受Characteristic被写的通知,收到蓝牙模块的数据后会触发mOnDataAvailable.onCharacteristicWrite()
                                bluetoothLeService.setCharacteristicNotification(
                                        characteristic, true);
                                writeCharacteristic=characteristic;
                            }
                        }

                    }


                }
                break;

        }

    }




    public boolean sendBytes(byte[] bytes){
        if (writeCharacteristic==null){
            return false;
        }
        writeCharacteristic.setValue(bytes);
        bluetoothLeService.writeCharacteristic(writeCharacteristic);
        return true;

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


        int[] separate = dataSeparate(bytes.length);

        for(int i =0;i<separate[0];i++)
        {
            byte[] sendb=new byte[20];
            System.arraycopy(bytes,20*i,sendb,0,20);
            sendBytes(sendb);
        }
        if(separate[1]!=0)
        {
            //调用蓝牙服务的写特征值方法实现发送数据
            byte[] sendb=new byte[ separate[1]];
            System.arraycopy(bytes,20*separate[0],sendb,0, separate[1]);
            sendBytes(sendb);
        }

        return sendBytes(bytes);
    }

    /**
     * 将数据分包
     *
     * **/
    private int[] dataSeparate(int len)
    {
        int[] lens = new int[2];
        lens[0]=len/20;
        lens[1]=len-20*lens[0];
        return lens;
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
