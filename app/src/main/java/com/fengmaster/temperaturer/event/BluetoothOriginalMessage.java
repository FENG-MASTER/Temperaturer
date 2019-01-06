package com.fengmaster.temperaturer.event;

/**
 * 蓝牙设备原始信息
 */
public class BluetoothOriginalMessage {

    private String message;

    private String data;


    public BluetoothOriginalMessage(String message, String data) {
        this.message = message;
        this.data = data;
    }

    public BluetoothOriginalMessage(String message) {
        this.message = message;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }
}
