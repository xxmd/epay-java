package org.example.request;

public interface EPayHttpInterceptor {
    EPayHttpResponse intercept(EPayHttpRequest request, Chain chain) throws Exception;

    interface Chain {
        EPayHttpResponse proceed(EPayHttpRequest request) throws Exception;
    }
}
