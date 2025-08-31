package org.example.entity.enums;

/**
 * 易支付
 */
public enum EPayAction implements ReadableEnum {
    QUERY("query", "查询"),
    SETTLE("settle", "结算"),
    ORDER("order", "单个订单"),
    ORDERS("orders", "多个订单");

    private final String value;
    private final String description;

    EPayAction(String value, String description) {
        this.value = value;
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
}
