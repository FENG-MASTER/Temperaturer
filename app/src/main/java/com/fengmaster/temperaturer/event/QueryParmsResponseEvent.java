package com.fengmaster.temperaturer.event;

import com.fengmaster.temperaturer.entry.QueryResponse;

/**
 * 查询参数信息返回事件
 * Created by FengMaster on 18/12/25.
 */
public class QueryParmsResponseEvent {

    private QueryResponse queryResponse;

    public QueryParmsResponseEvent(QueryResponse queryResponse) {
        this.queryResponse = queryResponse;
    }

    public QueryResponse getQueryResponse() {
        return queryResponse;
    }
}
