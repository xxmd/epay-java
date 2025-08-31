package org.example.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.stream.Collectors;

public class UrlBuilder {
    /**
     * 拼接请求参数
     *
     * @param paramMap        map集合
     * @param enableUrlEncode 开启url编码
     * @return 参数拼接结果
     */
    public static String concatParamMap(Map<String, String> paramMap, boolean enableUrlEncode) {
        return concatParamMap(paramMap, "=", "&", enableUrlEncode);
    }

    /**
     * 拼接请求参数
     *
     * @param paramMap        参数集合
     * @param kvDelimiter     key-value分隔符
     * @param paramDelimiter  参数分隔符
     * @param enableUrlEncode 开启url编码
     * @return
     */
    private static String concatParamMap(Map<String, String> paramMap, String kvDelimiter, String paramDelimiter, boolean enableUrlEncode) {
        if (paramMap == null || paramMap.isEmpty()) {
            return "";
        }
        return paramMap.entrySet().stream().filter(it -> StringUtils.isNotEmpty(it.getKey()) && StringUtils.isNotEmpty(it.getValue())).map(entry -> {
            try {
                String key = enableUrlEncode ? URLEncoder.encode(entry.getKey(), "UTF-8") : entry.getKey();
                String value = enableUrlEncode ? URLEncoder.encode(entry.getValue(), "UTF-8") : entry.getValue();
                return key + kvDelimiter + value;
            } catch (UnsupportedEncodingException e) {
                return "";
            }
        }).collect(Collectors.joining(paramDelimiter));
    }
}
