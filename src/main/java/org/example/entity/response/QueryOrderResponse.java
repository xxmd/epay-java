package org.example.entity.response;

import org.example.entity.enums.EPayType;

import java.math.BigDecimal;

public class QueryOrderResponse extends EPayResponse {
    // 易支付订单号
    private String tradeNo;
    // 商户订单号
    private String outTradeNo;
    // 第三方订单号
    private String apiTradeNo;
    // 支付方式
    private EPayType type;
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
    // 支付状态
    private int status;
    // 业务拓展参数
    private String param;
    // 支付者账号
    private String buyer;

    private boolean isPaySuccess() {
        return status == 1;
    }
}
