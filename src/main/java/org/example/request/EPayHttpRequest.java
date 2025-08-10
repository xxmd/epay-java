package org.example.request;

import java.util.HashMap;
import java.util.Map;

public class EPayHttpRequest {
    public enum METHOD {
        GET("GET"), POST("POST");
        private String value;

        METHOD(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    private String path;
    private METHOD method;
    private Map<String, String> headers = new HashMap<>();
    private Map<String, String> params = new HashMap<>();

    public EPayHttpRequest(METHOD method, String path) {
        this.method = method;
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public METHOD getMethod() {
        return method;
    }

    public void setMethod(METHOD method) {
        this.method = method;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void addRequestHeader(String key, String value) {
        headers.put(key, value);
    }

    public void addRequestParam(String key, String value) {
        params.put(key, value);
    }

    public void addRequestParams(Map<String, String> params) {
        this.params.putAll(params);
    }
}
