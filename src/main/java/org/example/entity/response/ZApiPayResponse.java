package org.example.entity.response;


import org.json.JSONObject;

/**
 * API接口支付响应结果
 */
public class ZApiPayResponse extends PayResponse {
    // 订单号
    private String tradeNo;
    // ZPay内部订单号
    private String orderId;
    // 支付跳转url
    private String payUrl;
    // 二维码链接
    private String qrCode;
    // 二维码图片
    private String img;

    public ZApiPayResponse(JSONObject jsonObject) {
        super(jsonObject);
        if (jsonObject == null) {
            return;
        }
        this.tradeNo = jsonObject.optString("trade_no");
        this.orderId = jsonObject.optString("O_id");
        this.payUrl = jsonObject.optString("payurl");
        this.qrCode = jsonObject.optString("qrcode");
        this.img = jsonObject.optString("img");
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getPayUrl() {
        return payUrl;
    }

    public String getQrCode() {
        return qrCode;
    }

    public String getImg() {
        return img;
    }
}
