package org.example.request.impl.interceptor;

import org.example.request.EPayHttpInterceptor;
import org.example.request.EPayHttpRequest;
import org.example.request.EPayHttpResponse;

public class EPayPidInterceptor implements EPayHttpInterceptor {
  private String pid;

  public EPayPidInterceptor(String pid) {
    this.pid = pid;
  }

  @Override
  public EPayHttpResponse intercept(EPayHttpRequest request, Chain chain) throws Exception {
    request.addRequestParam("pid", pid);
    return chain.proceed(request);
  }
}