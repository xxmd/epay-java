package org.example.entity.response;

import org.json.JSONObject;

public class QuerySettleResponse extends PayResponse {
    // 结算记录
    private Object[] data;

    public QuerySettleResponse(JSONObject jsonObject) {
        super(jsonObject);
    }
}
