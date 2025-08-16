package org.example.request.impl.interceptor;

import org.example.request.EPayHttpInterceptor;
import org.example.request.HttpRequest;
import org.example.request.EPayHttpResponse;

/**
 * 商户id拦截器
 */
public class PayPidInterceptor implements EPayHttpInterceptor {
    private String pid;

    public PayPidInterceptor(String pid) {
        this.pid = pid;
    }

    @Override
    public EPayHttpResponse intercept(HttpRequest request, Chain chain) throws Exception {
        request.addRequestParam("pid", pid);
        return chain.proceed(request);
    }
}