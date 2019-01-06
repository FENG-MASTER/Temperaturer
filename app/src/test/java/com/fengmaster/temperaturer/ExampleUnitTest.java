package com.fengmaster.temperaturer;

import com.alibaba.fastjson.JSONObject;
import com.fengmaster.temperaturer.entry.QueryResponse;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        QueryResponse queryResponse = JSONObject.parseObject("{aaa:11}", QueryResponse.class);
        assertEquals(4, 2 + 2);
    }
}