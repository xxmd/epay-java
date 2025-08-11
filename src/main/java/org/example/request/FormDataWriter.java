package org.example.request;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Map;

public class FormDataWriter implements RequestDataWriter {
    @Override
    public void write(Map<String, String> params, OutputStream outputStream) throws UnsupportedEncodingException {
        if (params == null || params.isEmpty()) {
            return;
        }
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream, "UTF-8"), true);
        String boundary = "----Boundary" + System.currentTimeMillis();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            writer.append("--").append(boundary).append("\r\n");
            writer.append("Content-Disposition: form-data; name=\"").append(entry.getKey()).append("\"").append("\r\n\r\n");
            writer.append(entry.getValue()).append("\r\n");
        }
        writer.append("--").append(boundary).append("--").append("\r\n");
        writer.flush();
    }
}
