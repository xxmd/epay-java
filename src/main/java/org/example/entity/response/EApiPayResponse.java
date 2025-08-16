package org.example.entity.response;

import org.json.JSONObject;

/**
 * API接口支付响应结果
 */
public class EApiPayResponse extends PayResponse {
    // 订单号
    private String tradeNo;
    // 支付跳转url
    private String payUrl;
    // 二维码链接
    private String qrCode;
    // 小程序跳转url
    private String urlScheme;

    public EApiPayResponse(JSONObject jsonObject) {
        super(jsonObject);
        if (jsonObject == null) {
            return;
        }
        this.tradeNo = jsonObject.optString("trade_no");
        this.payUrl = jsonObject.optString("payurl");
        this.qrCode = jsonObject.optString("qrcode");
        this.urlScheme = jsonObject.optString("urlscheme");
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public String getPayUrl() {
        return payUrl;
    }

    public String getQrCode() {
        return qrCode;
    }

    public String getUrlScheme() {
        return urlScheme;
    }

    public JSONObject toJsonObject() {
        JSONObject jsonObject = super.toJsonObject();
        jsonObject.put("tradeNo", tradeNo);
        jsonObject.put("payUrl", payUrl);
        jsonObject.put("qrCode", qrCode);
        jsonObject.put("urlScheme", urlScheme);
        return jsonObject;
    }

    @Override
    public String toString() {
        return toJsonObject().toString();
    }
}
