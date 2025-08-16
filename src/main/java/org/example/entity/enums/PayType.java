package org.example.entity.enums;

public enum PayType implements ReadableEnum {
    ALI_PAY("alipay", "支付宝"),
    WX_PAY("wxpay", "微信支付"),
    QQ_PAY("qqpay", "QQ钱包"),
    BANK("bank", "网银支付"),
    JD_PAY("jdpay", "京东支付"),
    PAY_PAL("paypal", "PayPal"),
    USDT("usdt", "泰达币"),
    UNKNOWN("unknown", "未知");

    private final String value;
    private final String description;

    PayType(String value, String description) {
        this.value = value;
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public static PayType valueOfIgnoreCase(String value) {
        if (value == null || value.equals("")) {
            return UNKNOWN;
        }
        for (PayType payType : values()) {
            if (payType.getValue().equals(value)) {
                return payType;
            }
        }
        return PayType.UNKNOWN;
    }
}
