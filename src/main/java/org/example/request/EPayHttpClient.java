package org.example.request;


import org.example.util.UrlBuilder;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 易支付Http客户端
 */
public class EPayHttpClient {
    // 基础路径
    private String baseUrl;
    // 拦截器集合
    private List<EPayHttpInterceptor> interceptors;
    // 拦截器链
    private EPayHttpInterceptor.Chain chain;

    public EPayHttpClient(String baseUrl) {
        this.baseUrl = baseUrl;
        this.interceptors = new ArrayList<>();
        this.chain = new RealInterceptorChain();
    }

    public void addInterceptor(EPayHttpInterceptor interceptor) {
        interceptors.add(interceptor);
    }

    public void addInterceptor(int index, EPayHttpInterceptor interceptor) {
        interceptors.add(index, interceptor);
    }

    public List<EPayHttpInterceptor> getInterceptors() {
        return interceptors;
    }

    public EPayHttpResponse execute(HttpRequest request) throws Exception {
        return chain.proceed(request);
    }

    class RealInterceptorChain implements EPayHttpInterceptor.Chain {
        private int index;

        RealInterceptorChain(int index) {
            this.index = index;
        }

        RealInterceptorChain() {

        }

        @Override
        public EPayHttpResponse proceed(HttpRequest request) throws Exception {
            if (index >= interceptors.size()) {
                // 链执行结束，模拟发请求
                return doRealRequest(request);
            }

            // 下一个拦截器
            EPayHttpInterceptor interceptor = interceptors.get(index);
            RealInterceptorChain next = new RealInterceptorChain(index + 1);
            return interceptor.intercept(request, next);
        }


        /**
         * 执行真实网络请求
         *
         * @param request 网络请求
         * @return 响应内容
         */
        private EPayHttpResponse doRealRequest(HttpRequest request) {
            EPayHttpResponse response = new EPayHttpResponse();
            try {
                String path = request.getPath();
                // 请求链接和请求方式处理
                if (HttpRequest.Method.GET.equals(request.getMethod()) && isNotEmptyMap(request.getParams())) {
                    path = request.getPath() + "?" + UrlBuilder.concatParamMap(request.getParams(), true);
                }
                URL url = new URL(baseUrl + path);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod(request.getMethod().getValue());

                // 请求头处理
                for (Map.Entry<String, String> entry : request.getHeaders().entrySet()) {
                    conn.setRequestProperty(entry.getKey(), entry.getValue());
                }

                Map<String, Object> logMap = new HashMap<>();
                logMap.put("requestUrl", url.toString());
                logMap.put("headers", request.getHeaders());
                logMap.put("params", request.getParams());
                System.out.println("Request detail: " + new JSONObject(logMap));

                // 请求体处理
                if (HttpRequest.Method.POST.equals(request.getMethod()) && isNotEmptyMap(request.getParams())) {
                    HttpRequest.ContentType contentType = request.getContentType();
                    if (contentType != null) {
                        conn.setDoOutput(true);
                        RequestDataWriter writer = RequestDataWriterFactory.getWriter(contentType);
                        if (writer != null) {
                            writer.write(request.getParams(), conn.getOutputStream());
                        }
                    }
                }

                // 响应内容处理
                int status = conn.getResponseCode();
                response.setStatusCode(status);
                InputStream is = (status < HttpURLConnection.HTTP_BAD_REQUEST)
                        ? conn.getInputStream()
                        : conn.getErrorStream();
                byte[] bytes = readInputStream(is);
                response.setData(bytes);
                conn.disconnect();
            } catch (Exception e) {
                response.setStatusCode(-1);
                response.setMessage(e.getMessage());
                e.printStackTrace();
            }
            return response;
        }

        private boolean isNotEmptyMap(Map<String, String> map) {
            return map != null && map.size() > 0;
        }

        /**
         * 读取输入流数据
         *
         * @param inputStream 输入流
         * @return 字节数组
         */
        private byte[] readInputStream(InputStream inputStream) throws IOException {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            byte[] data = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(data)) != -1) {
                buffer.write(data, 0, bytesRead);
            }
            return buffer.toByteArray();
        }
    }
}
