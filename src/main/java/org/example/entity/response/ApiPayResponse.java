package org.example.entity.response;

/**
 * API接口支付响应结果
 */
public class ApiPayResponse extends EPayResponse {
    // 订单号
    private String tradeNo;
    // 支付跳转url
    private String payUrl;
    // 二维码链接
    private String qrCode;
    // 小程序跳转url
    private String urlScheme;

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
}
