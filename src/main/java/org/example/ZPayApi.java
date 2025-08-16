package org.example;

import org.example.entity.PayRequestParam;
import org.example.entity.response.*;
import org.example.request.EPayHttpInterceptor;
import org.example.request.HttpRequest;
import org.example.request.EPayHttpResponse;
import org.example.util.ReflectUtils;
import org.json.JSONObject;

import java.util.Map;
import java.util.function.Consumer;

/**
 * ZPay支付接口
 */
public class ZPayApi extends PayApi implements IZPayApi {
    // 支付渠道id
    private String channelId;

    public ZPayApi(String pid, String key, String hostName, String channelId) {
        super(pid, key, hostName);
        this.channelId = channelId;
        addInterceptor(new EPayHttpInterceptor() {
            @Override
            public EPayHttpResponse intercept(HttpRequest request, Chain chain) throws Exception {
                request.setContentType(HttpRequest.ContentType.X_WWW_FORM_URLENCODED);
                return chain.proceed(request);
            }
        });
    }

    @Override
    public ZApiPayResponse apiInterfacePay(PayRequestParam payRequestParam) throws Exception {
        return apiInterfacePay(payRequestParam, null);
    }

    @Override
    public ZApiPayResponse apiInterfacePay(PayRequestParam payRequestParam, Consumer<HttpRequest> requestModifier) throws Exception {
        Map<String, String> fieldMap = ReflectUtils.getObjFields(payRequestParam);
        HttpRequest request = new HttpRequest(HttpRequest.Method.POST, "/mapi.php");
        request.addRequestParams(fieldMap);
        Consumer<HttpRequest> finalRequestModifier = requestModifier;
        Consumer<HttpRequest> enhanceRequestModifier = new Consumer<HttpRequest>() {
            @Override
            public void accept(HttpRequest request) {
                if (finalRequestModifier != null) {
                    finalRequestModifier.accept(request);
                }
                request.addRequestParam("cid", channelId);
            }
        };
        if (enhanceRequestModifier != null) {
            enhanceRequestModifier.accept(request);
        }
        EPayHttpResponse response = httpClient.execute(request);
        String jsonStr = response.getDataAsString();
        ZApiPayResponse apiPayResponse = new ZApiPayResponse(new JSONObject(jsonStr));
        return apiPayResponse;
    }
}
