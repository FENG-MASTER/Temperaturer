package com.fengmaster.temperaturer.entry;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;

import java.util.ArrayList;
import java.util.List;

/**
 * 设置参数实体对象
 * Created by FengMaster on 19/01/07.
 */
public class SetParmsRequest implements IPacks{

    public SetParmsRequest(QueryResponse queryResponse){
        try {
            if (queryResponse.getK1()!=null){
                setK1((TriggerParms) queryResponse.getK1().clone());
            }
            if (queryResponse.getK2()!=null){
                setK2((TriggerParms) queryResponse.getK2().clone());
            }
            if (queryResponse.getK3()!=null) {
                setK3((TriggerParms) queryResponse.getK3().clone());
            }
            if (queryResponse.getK4()!=null) {
                setK4((TriggerParms) queryResponse.getK4().clone());
            }
            if (queryResponse.getRH1()!=null){
                setRH1((TRHParms) queryResponse.getRH1().clone());
            }
            if (queryResponse.getRH2()!=null){
                setRH2((TRHParms) queryResponse.getRH2().clone());
            }
            if (queryResponse.getRH3()!=null){
                setRH3((TRHParms) queryResponse.getRH3().clone());
            }
            if (queryResponse.getK1()!=null){
                setT1((TRHParms) queryResponse.getT1().clone());
            }
            if (queryResponse.getK2()!=null){
                setT2((TRHParms) queryResponse.getT2().clone());
            }

            if (queryResponse.getK3()!=null){
                setT3((TRHParms) queryResponse.getT3().clone());
            }

        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }


    }

    private String type="Set";

    private String SN;

    private TRHParms T1;

    private TRHParms T2;

    private TRHParms T3;


    private TRHParms RH1;

    private TRHParms RH2;

    private TRHParms RH3;


    private TriggerParms K1;

    private TriggerParms K2;

    private TriggerParms K3;

    private TriggerParms K4;

    @JSONField(name = "Type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @JSONField(name = "SN")
    public String getSN() {
        return SN;
    }

    public void setSN(String SN) {
        this.SN = SN;
    }

    @JSONField(name = "T1")
    public TRHParms getT1() {
        return T1;
    }

    public void setT1(TRHParms t1) {
        T1 = t1;
    }
    @JSONField(name = "T2")
    public TRHParms getT2() {
        return T2;
    }

    public void setT2(TRHParms t2) {
        T2 = t2;
    }

    @JSONField(name = "T3")
    public TRHParms getT3() {
        return T3;
    }

    public void setT3(TRHParms t3) {
        T3 = t3;
    }

    @JSONField(name = "RH1")
    public TRHParms getRH1() {
        return RH1;
    }

    public void setRH1(TRHParms RH1) {
        this.RH1 = RH1;
    }

    @JSONField(name = "RH2")
    public TRHParms getRH2() {
        return RH2;
    }

    public void setRH2(TRHParms RH2) {
        this.RH2 = RH2;
    }

    @JSONField(name = "RH3")
    public TRHParms getRH3() {
        return RH3;
    }

    public void setRH3(TRHParms RH3) {
        this.RH3 = RH3;
    }

    @JSONField(name = "K1")
    public TriggerParms getK1() {
        return K1;
    }

    public void setK1(TriggerParms k1) {
        K1 = k1;
    }

    @JSONField(name = "K2")
    public TriggerParms getK2() {
        return K2;
    }

    public void setK2(TriggerParms k2) {
        K2 = k2;
    }

    @JSONField(name = "K3")
    public TriggerParms getK3() {
        return K3;
    }

    public void setK3(TriggerParms k3) {
        K3 = k3;
    }

    @JSONField(name = "K4")
    public TriggerParms getK4() {
        return K4;
    }

    public void setK4(TriggerParms k4) {
        K4 = k4;
    }

    @Override
    public List<String> getStrPacks(String name) {
        List<String> stringList=new ArrayList<>();
        stringList.add("{\"SN\":"+getSN()+"}");

        stringList.addAll(getT1().getStrPacks("T1"));
        stringList.addAll(getT2().getStrPacks("T2"));
        stringList.addAll(getT3().getStrPacks("T3"));

        stringList.addAll(getK1().getStrPacks("K1"));
        stringList.addAll(getK2().getStrPacks("K2"));
        stringList.addAll(getK3().getStrPacks("K3"));
        stringList.addAll(getK4().getStrPacks("K4"));

        return stringList;
    }
}
