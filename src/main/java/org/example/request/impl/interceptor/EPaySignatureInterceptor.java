package org.example.request.impl.interceptor;

import org.example.entity.enums.EPaySignType;
import org.example.request.EPayHttpInterceptor;
import org.example.request.EPayHttpRequest;
import org.example.request.EPayHttpResponse;
import org.example.util.Md5Utils;

import java.util.TreeMap;
import java.util.stream.Collectors;

public class EPaySignatureInterceptor implements EPayHttpInterceptor {
    private EPaySignType signType = EPaySignType.MD5;
    private String key;

    public EPaySignatureInterceptor(String key, EPaySignType signType) {
        this.key = key;
        this.signType = signType;
    }

    @Override
    public EPayHttpResponse intercept(EPayHttpRequest request, Chain chain) throws Exception {
        TreeMap<String, String> treeMap = new TreeMap<>(request.getParams());
        String concatParamStr = treeMap.entrySet().stream().map(it -> it.getKey() + "=" + it.getValue()).collect(Collectors.joining("&"));
        String signature = "";
        switch (signType) {
            case MD5:
                signature = Md5Utils.md5(concatParamStr + key);
                break;
        }
        request.addRequestParam("sign", signature);
        request.addRequestParam("sign_type", signType.getValue());
        return chain.proceed(request);
    }
}