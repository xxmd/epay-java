package org.example.entity.response;

import org.json.JSONObject;

public class PayResponse {

    public PayResponse(JSONObject jsonObject) {
        if (jsonObject == null) {
            return;
        }
        this.code = jsonObject.optString("code");
        this.msg = jsonObject.optString("msg");
    }

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

    public JSONObject toJsonObject() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", code);
        jsonObject.put("msg", msg);
        return jsonObject;
    }

    @Override
    public String toString() {
        return toJsonObject().toString();
    }
}
