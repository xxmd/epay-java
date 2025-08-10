package org.example.entity.response;

public class EPayResponse {
    // 返回状态码
    private String code;
    // 返回信息
    private String msg;

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public boolean isSuccess() {
        return "1".equals(code);
    }
}
