package com.fengmaster.temperaturer.entry;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.fengmaster.temperaturer.BR;

import java.util.ArrayList;
import java.util.List;


/**
 * 触发器参数对象
 * Created by FengMaster on 18/12/26.
 */
public class TriggerParms extends BaseObservable implements IPacks{

    private String Relation;

    private String Min;

    private String Max;

    private String Mode;

    private String State;

    @Bindable
    @JSONField(name = "R")
    public String getRelation() {
        return Relation;
    }

    @JSONField(name = "R")
    public void setRelation(String relation) {
        Relation = relation;
        notifyPropertyChanged(BR.relation);
    }

    @Bindable
    @JSONField(name = "Min")
    public String getMin() {
        return Min;
    }

    @JSONField(name = "Min")
    public void setMin(String min) {
        Min = min;
        notifyPropertyChanged(BR.min);
    }

    @Bindable
    @JSONField(name = "Max")
    public String getMax() {
        return Max;
    }

    @JSONField(name = "Max")
    public void setMax(String max) {
        Max = max;
        notifyPropertyChanged(BR.max);
    }

    @Bindable
    @JSONField(name = "M")
    public String getMode() {
        return Mode;
    }

    @JSONField(name = "M")
    public void setMode(String mode) {
        Mode = mode;
        notifyPropertyChanged(BR.mode);
    }

    @Bindable
    @JSONField(name = "S")
    public String getState() {
        return State;
    }

    @JSONField(name = "S")
    public void setState(String state) {
        State = state;
        notifyPropertyChanged(BR.state);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        TriggerParms c=JSONObject.parseObject(JSONObject.toJSONString(this),TriggerParms.class);
        return c;
    }

    @Override
    public List<String> getStrPacks(String name) {
        List<String> strings=new ArrayList<>();
        strings.add("{\""+name+"\":{\"R\":\""+getRelation()+"\"}}");
        strings.add("{\""+name+"\":{\"Min\":"+getMin()+"}}");
        strings.add("{\""+name+"\":{\"Max\":"+getMax()+"}}");
        strings.add("{\""+name+"\":{\"M\":\""+getMode()+"\"}}");
        return strings;
    }
}
