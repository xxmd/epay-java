package org.example.request;

import java.io.OutputStream;
import java.util.Map;

/**
 * 请求数据写入器
 */
public interface RequestDataWriter {
    void write(Map<String, String> params, OutputStream outputStream) throws Exception;
}
