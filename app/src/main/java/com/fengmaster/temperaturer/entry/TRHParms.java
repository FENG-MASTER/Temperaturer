package com.fengmaster.temperaturer.entry;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.fengmaster.temperaturer.BR;


/**
 * 温度和湿度参数
 * Created by FengMaster on 18/12/26.
 */
public class TRHParms extends BaseObservable {

    private String A;

    private String B;

    @Bindable
    @JSONField(name = "A")
    public String getA() {
        return A;
    }

    public void setA(String a) {
        A = a;
        notifyPropertyChanged(BR.a);
    }

    @Bindable
    @JSONField(name = "B")
    public String getB() {
        return B;
    }

    public void setB(String b) {
        B = b;
        notifyPropertyChanged(BR.b);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        TRHParms c=JSONObject.parseObject(JSONObject.toJSONString(this),TRHParms.class);
        return c;
    }
}
