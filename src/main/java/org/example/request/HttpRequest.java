package org.example.request;

import java.util.HashMap;
import java.util.Map;

public class HttpRequest {

    public enum Method {
        GET("GET"), POST("POST");
        private String value;

        Method(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public enum ContentType {
        X_WWW_FORM_URLENCODED("application/x-www-form-urlencoded"), FORM_DATA("multipart/form-data");
        private String value;

        ContentType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    // 请求路径
    private String path;
    // 请求方式
    private Method method;
    // 请求方式
    private ContentType contentType;
    // 请求头信息
    private Map<String, String> headers = new HashMap<>();
    // 请求参数
    private Map<String, String> params = new HashMap<>();

    public HttpRequest(Method method, String path) {
        this.method = method;
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
        addRequestHeader("Content-Type", contentType.getValue());
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
