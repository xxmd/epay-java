package org.example.entity.enums;

public enum EPayType implements ReadableEnum {
    ALI_PAY("alipay", "支付宝"),
    WX_PAY("wxpay", "微信支付"),
    QQ_PAY("qqpay", "QQ钱包"),
    BANK("bank", "网银支付"),
    JD_PAY("jdpay", "京东支付"),
    PAY_PAL("paypal", "PayPal"),
    USDT("usdt", "泰达币");

    private final String value;
    private final String description;

    EPayType(String value, String description) {
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
