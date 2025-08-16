package org.example.entity.response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class QueryOrdersResponse extends PayResponse {
    // 易支付订单号
    private List<QueryOrderResponse> orderList = new ArrayList<>();

    public QueryOrdersResponse(JSONObject jsonObject) {
        super(jsonObject);
        JSONArray jsonArray = jsonObject.optJSONArray("data");
        if (jsonArray != null) {
            for (int i = 0; i < jsonArray.length(); i++) {
                orderList.add(new QueryOrderResponse(jsonArray.getJSONObject(i)));
            }
        }
    }
}
