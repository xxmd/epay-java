package org.example.entity.response;

import org.json.JSONObject;

public class QueryOrdersResponse extends PayResponse {
    // 易支付订单号
    private Object[] data;

    public QueryOrdersResponse(JSONObject jsonObject) {
        super(jsonObject);
    }
}
