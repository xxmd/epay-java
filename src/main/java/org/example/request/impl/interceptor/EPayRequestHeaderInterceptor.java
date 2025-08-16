package org.example.request.impl.interceptor;

import org.example.request.EPayHttpInterceptor;
import org.example.request.HttpRequest;
import org.example.request.EPayHttpResponse;

public class EPayRequestHeaderInterceptor implements EPayHttpInterceptor {

  @Override
  public EPayHttpResponse intercept(HttpRequest request, Chain chain) throws Exception {
    request.addRequestHeader("Content-Type", "multipart/form-data");
    return chain.proceed(request);
  }
}