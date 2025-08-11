package org.example;

import com.google.gson.Gson;
import org.example.entity.PayRequestParam;
import org.example.entity.enums.EPaySignType;
import org.example.entity.response.*;
import org.example.request.EPayHttpClient;
import org.example.request.EPayHttpInterceptor;
import org.example.request.EPayHttpRequest;
import org.example.request.EPayHttpResponse;
import org.example.request.impl.interceptor.EPayPidInterceptor;
import org.example.request.impl.interceptor.EPaySignatureInterceptor;
import org.example.util.Md5Utils;
import org.example.util.ReflectUtils;
import org.example.util.UrlBuilder;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Consumer;

public class EPayApi implements IEPayApi {
    private String pid;
    private String key;
    private String baseUrl;
    private EPayHttpClient httpClient;
    private Gson gson;
    private int fixedInterceptorSize;

    public EPayApi(String pid, String key, String hostName) {
        this.pid = pid;
        this.key = key;
        this.baseUrl = "https://" + hostName;
        this.gson = new Gson();
        initHttpClient();
    }

    private void initHttpClient() {
        httpClient = new EPayHttpClient(this.baseUrl);
        List<EPayHttpInterceptor> interceptorList = new ArrayList<>();
        interceptorList.add(new EPayPidInterceptor(pid));
        interceptorList.add(new EPaySignatureInterceptor(key, EPaySignType.MD5));
        fixedInterceptorSize = interceptorList.size();
        for (EPayHttpInterceptor interceptor : interceptorList) {
            httpClient.addInterceptor(interceptor);
        }
    }

    @Override
    public void addInterceptor(EPayHttpInterceptor interceptor) {
        int size = httpClient.getInterceptors().size();
        httpClient.addInterceptor(size - fixedInterceptorSize, interceptor);
    }

    @Override
    public String pageRedirectPay(PayRequestParam payRequestParam) throws Exception {
        return pageRedirectPay(payRequestParam, null);
    }

    @Override
    public String pageRedirectPay(PayRequestParam payRequestParam, Map<String, String> extraParams) throws Exception {
        Map<String, String> fieldMap = ReflectUtils.getObjFields(payRequestParam);
        fieldMap.put("pid", pid);
        TreeMap<String, String> sortedParamMap = new TreeMap<>(fieldMap);
        if (extraParams != null) {
            sortedParamMap.putAll(extraParams);
        }
        String concatParamStr = UrlBuilder.concatParamMap(sortedParamMap, false);
        String signature = Md5Utils.md5(concatParamStr + key);
        sortedParamMap.put("sign", signature);
        sortedParamMap.put("sign_type", EPaySignType.MD5.getValue());
        String queryStr = UrlBuilder.concatParamMap(sortedParamMap, true);
        return String.format(Locale.US, "%s/%s?%s", baseUrl, "submit.php", queryStr);
    }

    @Override
    public ApiPayResponse apiInterfacePay(PayRequestParam payRequestParam) throws Exception {
        return apiInterfacePay(payRequestParam, null);
    }

    @Override
    public ApiPayResponse apiInterfacePay(PayRequestParam payRequestParam, Consumer<EPayHttpRequest> requestModifier) throws Exception {
        Map<String, String> fieldMap = ReflectUtils.getObjFields(payRequestParam);
        EPayHttpRequest request = new EPayHttpRequest(EPayHttpRequest.Method.POST, "/mapi.php");
        request.addRequestParams(fieldMap);
        if (requestModifier != null) {
            requestModifier.accept(request);
        }
        EPayHttpResponse response = httpClient.execute(request);
        String jsonStr = response.getDataAsString();
        ApiPayResponse apiPayResponse = gson.fromJson(jsonStr, ApiPayResponse.class);
        return apiPayResponse;
    }

    @Override
    public QueryMerchantResponse queryMerchantInfo(String pid, String key) {
        return null;
    }

    @Override
    public QuerySettleResponse querySettleRecord(String tradeNo, String outTradeNo) {
        return null;
    }

    @Override
    public QueryOrderResponse queryOrder(String tradeNo, String outTradeNo) {
        return null;
    }

    @Override
    public QueryOrdersResponse queryOrders(int limit, int page) {
        return null;
    }

    @Override
    public EPayResponse refund(String tradeNo, String outTradeNo, BigDecimal money) {
        return null;
    }

}
