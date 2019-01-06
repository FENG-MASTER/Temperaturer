package com.fengmaster.temperaturer.entry;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.fengmaster.temperaturer.BR;


/**
 * 触发器参数对象
 * Created by FengMaster on 18/12/26.
 */
public class TriggerParms extends BaseObservable {

    private String Relation;

    private String Min;

    private String Max;

    private String Mode;

    private String State;

    @Bindable
    @JSONField(name = "Relation")
    public String getRelation() {
        return Relation;
    }

    public void setRelation(String relation) {
        Relation = relation;
        notifyPropertyChanged(BR.relation);
    }

    @Bindable
    @JSONField(name = "Min")
    public String getMin() {
        return Min;
    }

    public void setMin(String min) {
        Min = min;
        notifyPropertyChanged(BR.min);
    }

    @Bindable
    @JSONField(name = "Max")
    public String getMax() {
        return Max;
    }

    public void setMax(String max) {
        Max = max;
        notifyPropertyChanged(BR.max);
    }

    @Bindable
    @JSONField(name = "Mode")
    public String getMode() {
        return Mode;
    }

    public void setMode(String mode) {
        Mode = mode;
        notifyPropertyChanged(BR.mode);
    }

    @Bindable
    @JSONField(name = "State")
    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
        notifyPropertyChanged(BR.state);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        TriggerParms c=JSONObject.parseObject(JSONObject.toJSONString(this),TriggerParms.class);
        return c;
    }

}
