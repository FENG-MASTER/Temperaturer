package com.fengmaster.temperaturer.util;

/**
 * 格式化工具
 * Created by FengMaster on 19/02/18.
 */
public class FormatUtil {

    public static String formatDoublePoint(String dstr,int p){
        if (dstr==null||dstr.isEmpty()){
            return "";
        }
        double aDouble = Double.parseDouble(dstr);
        return String.format("%.2f", aDouble);
    }


    public static String formatDoublePoint(String dstr){
        return formatDoublePoint(dstr,2);
    }
}
