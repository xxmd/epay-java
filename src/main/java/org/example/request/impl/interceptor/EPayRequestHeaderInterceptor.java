package org.example.request.impl.interceptor;

import org.example.request.EPayHttpInterceptor;
import org.example.request.EPayHttpRequest;
import org.example.request.EPayHttpResponse;

public class EPayRequestHeaderInterceptor implements EPayHttpInterceptor {

  @Override
  public EPayHttpResponse intercept(EPayHttpRequest request, Chain chain) throws Exception {
    request.addRequestHeader("Content-Type", "x-www-form-urlencode");
    return chain.proceed(request);
  }
}