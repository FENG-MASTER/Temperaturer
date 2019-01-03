package com.fengmaster.temperaturer.bluetooth.base;

/**
 * Created by FengMaster on 18/12/24.
 */
public class BluetoothModel {

    /**
     * 蓝牙地址
     */
    private String address;

    /**
     * 设备名称
     */
    private String deviceName;

    /**
     * 设备密码
     */
    private String password;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public boolean equals(Object obj) {
        return address.equals(((BluetoothModel)obj).address);
    }

    @Override
    public int hashCode() {
        return address.hashCode();
    }
}
