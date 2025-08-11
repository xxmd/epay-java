package org.example.request;

import java.net.HttpURLConnection;

public interface EPayContentTypeHandler {
    void handle(String contentType, HttpURLConnection connection, EPayHttpRequest request);
}
