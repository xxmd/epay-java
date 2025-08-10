package org.example.request;

import java.net.HttpURLConnection;

public class EPayHttpResponse {
    private int statusCode;
    private String message;
    private byte[] data;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public byte[] getData() {
        return data;
    }

    public String getDataAsString() {
        return new String(data);
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return statusCode < HttpURLConnection.HTTP_BAD_REQUEST;
    }
}
