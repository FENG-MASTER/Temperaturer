package com.fengmaster.temperaturer.entry;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.fengmaster.temperaturer.BR;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * 温度和湿度参数
 * Created by FengMaster on 18/12/26.
 */
public class TRHParms extends BaseObservable implements IPacks {

    private String A;

    private String B;

    @Bindable
    @JSONField(name = "A")
    public String getA() {
        return A;
    }

    public void setA(String a) {
        if (a==null||a.isEmpty()||a.endsWith(".")){
            return;
        }

        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(1);
        A = nf.format(Double.valueOf(a));
        notifyPropertyChanged(BR.a);
    }

    @Bindable
    @JSONField(name = "B")
    public String getB() {
        return B;
    }

    public void setB(String b) {
        if (b==null||b.isEmpty()||b.endsWith(".")){
            return;
        }
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(1);
        B = nf.format(Double.valueOf(b));
        notifyPropertyChanged(BR.b);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        TRHParms c = JSONObject.parseObject(JSONObject.toJSONString(this), TRHParms.class);
        return c;
    }

    @Override
    public List<String> getStrPacks(String name) {
        List<String> strings = new ArrayList<>();
        strings.add("{\"" + name + "\":{\"A\":" + getA() + "}}");
        strings.add("{\"" + name + "\":{\"B\":" + getB() + "}}");
        return strings;
    }
}
