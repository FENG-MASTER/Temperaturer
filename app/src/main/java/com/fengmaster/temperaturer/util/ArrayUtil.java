package com.fengmaster.temperaturer.util;

import com.fengmaster.temperaturer.App;

/**
 * Created by FengMaster on 18/12/27.
 */
public class ArrayUtil {

    public static int getPostion(int resId,String s){
        if (s==null){
            return 0;
        }
        String[] stringArray = App.getContext().getResources().getStringArray(resId);
        for (int i = 0; i < stringArray.length; i++) {
            if (stringArray[i].toLowerCase().equals(s.toLowerCase())){
                return i;
            }
        }
        return 0;
    }

    public static String getString(int resId,int i){
        String[] stringArray = App.getContext().getResources().getStringArray(resId);
        if (i<0||i>stringArray.length){
            return "";
        }

        return stringArray[i];
    }


}
