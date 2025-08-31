package org.example.entity.enums;

public enum EPaySignType implements ReadableEnum {
    MD5("MD5", "MD5签名");

    private final String value;
    private final String description;

    EPaySignType(String value, String description) {
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
