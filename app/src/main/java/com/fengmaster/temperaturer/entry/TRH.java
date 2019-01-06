package com.fengmaster.temperaturer.entry;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.fengmaster.temperaturer.BR;


/**
 * 温度和湿度情况
 * Created by FengMaster on 18/12/25.
 */
public class TRH extends BaseObservable {

    private String T;


    private String RH;

    @Bindable
    @JSONField(name = "T")
    public String getT() {
        return T;
    }

    public void setT(String t) {
        T = t;
        notifyPropertyChanged(BR.t);
    }

    @Bindable
    @JSONField(name = "RH")
    public String getRH() {
        return RH;
    }

    public void setRH(String RH) {
        this.RH = RH;
        notifyPropertyChanged(BR.rH);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        TRH c=JSONObject.parseObject(JSONObject.toJSONString(this),TRH.class);
        return c;
    }
}
