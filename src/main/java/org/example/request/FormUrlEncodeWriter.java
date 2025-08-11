package org.example.request;

import org.example.util.UrlBuilder;

import java.io.*;
import java.util.Map;

public class FormUrlEncodeWriter implements RequestDataWriter {
    @Override
    public void write(Map<String, String> params, OutputStream outputStream) throws IOException {
        if (params == null || params.isEmpty()) {
            return;
        }
        String requestBody = UrlBuilder.concatParamMap(params, true);
        if (requestBody != null) {
            outputStream.write(requestBody.getBytes());
        }
    }
}
