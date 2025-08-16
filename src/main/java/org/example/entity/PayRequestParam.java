package org.example.entity;

import org.example.entity.enums.EPayDevice;
import org.example.entity.enums.PayType;

import java.math.BigDecimal;

/**
 * 支付请求参数
 */
public class PayRequestParam {
    // 支付方式
    private PayType type;
    // 商户订单号
    private String outTradeNo;
    // 异步通知地址
    private String notifyUrl;
    // 跳转通知地址
    private String returnUrl;
    // 商品名称
    private String name;
    // 商品金额
    private BigDecimal money;
    // 用户IP地址
    private String clientip;
    // 设备信息
    private EPayDevice device;
    // 业务拓展参数
    private String param;

    public PayType getType() {
        return type;
    }

    public void setType(PayType type) {
        this.type = type;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public String getClientIp() {
        return clientip;
    }

    public void setClientIp(String clientIp) {
        this.clientip = clientIp;
    }

    public EPayDevice getDevice() {
        return device;
    }

    public void setDevice(EPayDevice device) {
        this.device = device;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }
}
