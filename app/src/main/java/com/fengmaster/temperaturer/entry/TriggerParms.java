package com.fengmaster.temperaturer.entry;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
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
    public String getRelation() {
        return Relation;
    }

    public void setRelation(String relation) {
        Relation = relation;
        notifyPropertyChanged(BR.relation);
    }

    @Bindable
    public String getMin() {
        return Min;
    }

    public void setMin(String min) {
        Min = min;
        notifyPropertyChanged(BR.min);
    }

    @Bindable
    public String getMax() {
        return Max;
    }

    public void setMax(String max) {
        Max = max;
        notifyPropertyChanged(BR.max);
    }

    @Bindable
    public String getMode() {
        return Mode;
    }

    public void setMode(String mode) {
        Mode = mode;
        notifyPropertyChanged(BR.mode);
    }

    @Bindable
    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
        notifyPropertyChanged(BR.state);
    }
}
