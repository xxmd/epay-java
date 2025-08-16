package org.example.entity.response;

import org.example.entity.enums.PayType;
import org.example.util.StringUtils;
import org.json.JSONObject;

import java.math.BigDecimal;

public class QueryOrderResponse extends PayResponse {
    // 易支付订单号
    private String tradeNo;
    // 商户订单号
    private String outTradeNo;
    // 第三方订单号
    private String apiTradeNo;
    // 支付方式
    private PayType type;
    // 商户ID
    private String pid;
    // 创建订单时间
    private String addTime;
    // 完成交易时间
    private String endTime;
    // 商品名称
    private String name;
    // 商品金额
    private BigDecimal money;
    // 支付状态(1=支付成功 0=未支付)
    private int status;
    // 业务拓展参数
    private String param;
    // 支付者账号
    private String buyer;

    public QueryOrderResponse(JSONObject jsonObject) {
        super(jsonObject);
        tradeNo = jsonObject.optString("trade_no");
        outTradeNo = jsonObject.optString("out_trade_no");
        String typeStr = jsonObject.optString("type");
        if (StringUtils.isNotEmpty(typeStr)) {
            type = PayType.valueOfIgnoreCase(typeStr);
        }
        pid = jsonObject.optString("pid");
        addTime = jsonObject.optString("addtime");
        endTime = jsonObject.optString("endtime");
        name = jsonObject.optString("name");
        String moneyStr = jsonObject.optString("money");
        if (StringUtils.isNotEmpty(moneyStr)) {
            money = new BigDecimal(moneyStr);
        }
        status = jsonObject.optInt("status");
        param = jsonObject.optString("param");
        buyer = jsonObject.optString("buyer");
    }

    public boolean isPaySuccess() {
        return status == 1;
    }

    @Override
    public String toString() {
        JSONObject jsonObject = toJsonObject();
        return jsonObject.toString();
    }

    @Override
    public JSONObject toJsonObject() {
        JSONObject jsonObject = super.toJsonObject();
        jsonObject.put("tradeNo", tradeNo);
        jsonObject.put("outTradeNo", outTradeNo);
        jsonObject.put("apiTradeNo", apiTradeNo);
        jsonObject.put("type", type);
        jsonObject.put("pid", pid);
        jsonObject.put("addTime", addTime);
        jsonObject.put("endTime", endTime);
        jsonObject.put("name", name);
        jsonObject.put("money", money);
        jsonObject.put("status", status);
        jsonObject.put("param", param);
        jsonObject.put("buyer", buyer);
        jsonObject.put("isPaySuccess", isPaySuccess());
        return jsonObject;
    }
}
