package org.example.entity;

import org.example.entity.enums.EPayDevice;
import org.example.entity.enums.EPaySignType;
import org.example.entity.enums.EPayType;

import java.math.BigDecimal;

public class PayResult {
    // 商户ID
    private String pid;
    // 易支付订单号
    private String tradeNo;
    // 商户订单号
    private String outTradeNo;
    // 支付方式
    private EPayType type;
    // 商品名称
    private String name;
    // 商品金额
    private BigDecimal money;
    // 业务拓展参数
    private String clientIp;
    // 用户IP地址
    private EPayDevice device;
    // 签名字符串
    private String sign;
    // 签名类型
    private EPaySignType signType;
}
