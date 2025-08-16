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
    private int active;
    // 商户余额
    private BigDecimal money;
    // 结算方式(1=支付宝 2=微信 3=QQ 4=银行卡 5=USDT)
    private int type;
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
        this.pid = jsonObject.optString("pid");
        this.key = jsonObject.optString("key");
        this.active = jsonObject.optInt("active");
        this.money = new BigDecimal(jsonObject.optString("money"));
        this.type = jsonObject.optInt("type");
        this.account = jsonObject.optString("account");
        this.username = jsonObject.optString("username");
        this.orders = jsonObject.optInt("orders");
        this.orderToday = jsonObject.optInt("order_today");
        this.orderLastday = jsonObject.optInt("order_lastday");
    }

    public JSONObject toJsonObject() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("pid", pid);
        jsonObject.put("key", key);
        jsonObject.put("active", active);
        jsonObject.put("money", money);
        jsonObject.put("type", type);
        jsonObject.put("account", account);
        jsonObject.put("username", username);
        jsonObject.put("username", username);
        jsonObject.put("orders", orders);
        jsonObject.put("orderToday", orderToday);
        jsonObject.put("orderLastday", orderLastday);
        return jsonObject;
    }

    @Override
    public String toString() {
        return toJsonObject().toString();
    }
}
