package org.example.request.impl.interceptor;

import org.example.request.EPayHttpInterceptor;
import org.example.request.HttpRequest;
import org.example.request.EPayHttpResponse;

public class EPayLogInterceptor implements EPayHttpInterceptor {


  @Override
  public EPayHttpResponse intercept(HttpRequest request, Chain chain) throws Exception {
    return chain.proceed(request);
  }
}