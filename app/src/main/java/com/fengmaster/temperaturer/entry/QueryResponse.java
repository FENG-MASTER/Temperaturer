package com.fengmaster.temperaturer.entry;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.fengmaster.temperaturer.BR;

/**
 * 查询命令返回的对象
 * Created by FengMaster on 18/12/26.
 */
public class QueryResponse extends BaseObservable {


    public QueryResponse() {
    }

    public void copy(QueryResponse queryResponse){
        setK1(queryResponse.getK1());
        setK2(queryResponse.getK2());
        setK3(queryResponse.getK3());
        setK4(queryResponse.getK4());
        setRH1(queryResponse.getRH1());
        setRH2(queryResponse.getRH2());
        setRH3(queryResponse.getRH3());
        setS1(queryResponse.getS1());
        setS2(queryResponse.getS2());
        setS3(queryResponse.getS3());
        setSN(queryResponse.getSN());
        setT1(queryResponse.getT1());
        setT2(queryResponse.getT2());
        setT3(queryResponse.getT3());
    }

    protected String type;

    protected String SN;

    protected TRH S1;

    protected TRH S2;

    protected TRH S3;

    protected TRHParms T1;

    protected TRHParms T2;

    protected TRHParms T3;


    protected TRHParms RH1;

    protected TRHParms RH2;

    protected TRHParms RH3;


    protected TriggerParms K1;

    protected TriggerParms K2;

    protected TriggerParms K3;

    protected TriggerParms K4;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Bindable
    public String getSN() {
        return SN;
    }

    public void setSN(String SN) {
        this.SN = SN;
        notifyPropertyChanged(BR.sN);
    }

    @Bindable
    public TRH getS1() {
        return S1;
    }

    public void setS1(TRH s1) {
        S1 = s1;
        notifyPropertyChanged(BR.s1);
    }

    @Bindable
    public TRH getS2() {
        return S2;
    }

    public void setS2(TRH s2) {
        S2 = s2;
        notifyPropertyChanged(BR.s2);
    }

    @Bindable
    public TRH getS3() {
        return S3;
    }

    public void setS3(TRH s3) {
        S3 = s3;
        notifyPropertyChanged(BR.s3);
    }

    @Bindable
    public TRHParms getT1() {
        return T1;
    }

    public void setT1(TRHParms t1) {
        T1 = t1;
        notifyPropertyChanged(BR.t1);
    }

    @Bindable
    public TRHParms getT2() {
        return T2;
    }

    public void setT2(TRHParms t2) {
        T2 = t2;
        notifyPropertyChanged(BR.t2);
    }

    @Bindable
    public TRHParms getT3() {
        return T3;
    }

    public void setT3(TRHParms t3) {
        T3 = t3;
        notifyPropertyChanged(BR.t3);
    }

    @Bindable
    public TRHParms getRH1() {
        return RH1;
    }

    public void setRH1(TRHParms RH1) {
        this.RH1 = RH1;
        notifyPropertyChanged(BR.rH1);

    }

    @Bindable
    public TRHParms getRH2() {
        return RH2;
    }

    public void setRH2(TRHParms RH2) {
        this.RH2 = RH2;
        notifyPropertyChanged(BR.rH2);
    }

    @Bindable
    public TRHParms getRH3() {
        return RH3;
    }

    public void setRH3(TRHParms RH3) {
        this.RH3 = RH3;
        notifyPropertyChanged(BR.rH3);
    }

    @Bindable
    public TriggerParms getK1() {
        return K1;
    }

    public void setK1(TriggerParms k1) {
        K1 = k1;
        notifyPropertyChanged(BR.k1);
    }

    @Bindable
    public TriggerParms getK2() {
        return K2;
    }

    public void setK2(TriggerParms k2) {
        K2 = k2;
        notifyPropertyChanged(BR.k2);
    }

    @Bindable
    public TriggerParms getK3() {
        return K3;
    }

    public void setK3(TriggerParms k3) {
        K3 = k3;
        notifyPropertyChanged(BR.k3);
    }

    @Bindable
    public TriggerParms getK4() {
        return K4;
    }

    public void setK4(TriggerParms k4) {
        K4 = k4;
        notifyPropertyChanged(BR.k4);
    }
}
