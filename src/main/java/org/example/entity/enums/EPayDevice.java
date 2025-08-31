package org.example.entity.enums;

/**
 * 易支付设备类型
 */
public enum EPayDevice implements ReadableEnum {
    PC("pc", "电脑浏览器"),
    MOBILE("mobile", "手机浏览器"),
    QQ_PAY("qq", "手机QQ内浏览器"),
    WECHAT("wechat", "微信内浏览器"),
    ALIPAY("alipay", "支付宝客户端");

    private final String value;
    private final String description;

    EPayDevice(String value, String description) {
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
