package org.example.entity.response;

import org.example.entity.enums.PayType;
import org.json.JSONObject;

import java.math.BigDecimal;

public class QueryMerchantResponse extends PayResponse {
    // 商户ID
    private String pid;
    // 商户密钥
    private String key;
    // 商户状态
    private boolean active;
    // 商户余额
    private BigDecimal money;
    // 结算方式
    private PayType type;
    // 结算账号
    private String account;
    // 订单姓名
    private String username;
    // 订单总数
    private int orders;
    // 今日订单
    private int orderToday;
    // 昨日订单
    private int orderLastday;

    public QueryMerchantResponse(JSONObject jsonObject) {
        super(jsonObject);
    }
}
