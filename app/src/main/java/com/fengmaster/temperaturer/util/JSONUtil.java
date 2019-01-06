package com.fengmaster.temperaturer.util;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

/**
 * Created by FengMaster on 19/01/07.
 */
public class JSONUtil {

    public static boolean isJSON(String s){
        if (s==null||s.isEmpty()){
            return false;
        }
        try {
            JSONObject.parse(s);
        }catch (JSONException e){
            try {
                JSONObject.parseArray(s);
            } catch (JSONException ex1) {
                return false;
            }
        }
        return true;
    }

}
