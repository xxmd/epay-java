package org.example;

import org.example.entity.PayRequestParam;
import org.example.entity.response.*;
import org.example.request.EPayHttpInterceptor;
import org.example.request.EPayHttpRequest;
import org.example.request.EPayHttpResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * ZPay支付接口
 */
public class ZPayApi extends EPayApi {
    // 支付渠道id
    private String channelId;

    public ZPayApi(String pid, String key, String hostName, String channelId) {
        super(pid, key, hostName);
        this.channelId = channelId;
        addInterceptor(new EPayHttpInterceptor() {
            @Override
            public EPayHttpResponse intercept(EPayHttpRequest request, Chain chain) throws Exception {
                request.setContentType(EPayHttpRequest.ContentType.X_WWW_FORM_URLENCODED);
                return chain.proceed(request);
            }
        });
    }

    @Override
    public String pageRedirectPay(PayRequestParam payRequestParam, Map<String, String> extraParams) throws Exception {
        if (extraParams == null) {
            extraParams = new HashMap<>();
        }
        if (!extraParams.containsKey("cid")) {
            extraParams.put("cid", channelId);
        }
        return super.pageRedirectPay(payRequestParam, extraParams);
    }

    @Override
    public ApiPayResponse apiInterfacePay(PayRequestParam payRequestParam) throws Exception {
        return apiInterfacePay(payRequestParam, null);
    }

    @Override
    public ApiPayResponse apiInterfacePay(PayRequestParam payRequestParam, Consumer<EPayHttpRequest> requestModifier) throws Exception {
        Consumer<EPayHttpRequest> finalRequestModifier = requestModifier;
        Consumer<EPayHttpRequest> enhanceRequestModifier = new Consumer<EPayHttpRequest>() {
            @Override
            public void accept(EPayHttpRequest request) {
                if (finalRequestModifier != null) {
                    finalRequestModifier.accept(request);
                }
                request.addRequestParam("cid", channelId);
            }
        };
        return super.apiInterfacePay(payRequestParam, enhanceRequestModifier);
    }
}
