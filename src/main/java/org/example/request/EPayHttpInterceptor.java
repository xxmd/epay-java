package org.example.request;

public interface EPayHttpInterceptor {
    EPayHttpResponse intercept(HttpRequest request, Chain chain) throws Exception;

    interface Chain {
        EPayHttpResponse proceed(HttpRequest request) throws Exception;
    }
}
