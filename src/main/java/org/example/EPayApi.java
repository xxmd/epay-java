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
import org.example.request.impl.interceptor.EPayRequestHeaderInterceptor;
import org.example.request.impl.interceptor.EPaySignatureInterceptor;
import org.example.util.Md5Utils;
import org.example.util.ReflectUtils;
import org.example.util.UrlBuilder;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

public class EPayApi implements IEPayApi {
    private String pid;
    private String key;
    private String baseUrl;
    private EPayHttpClient httpClient;
    private Gson gson;

    public EPayApi(String pid, String key, String hostName) {
        this.pid = pid;
        this.key = key;
        this.baseUrl = "https://" + hostName;
        this.gson = new Gson();
        initHttpClient();
    }

    private void initHttpClient() {
        httpClient = new EPayHttpClient(this.baseUrl);
        httpClient.addInterceptor(new EPayRequestHeaderInterceptor());
        httpClient.addInterceptor(new EPayPidInterceptor(pid));
        httpClient.addInterceptor(new EPaySignatureInterceptor(key, EPaySignType.MD5));
    }

    @Override
    public void addInterceptor(EPayHttpInterceptor interceptor) {
        int size = httpClient.getInterceptors().size();
        httpClient.addInterceptor(size - 3, interceptor);
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
    public ApiPayResponse apiInterfacePay(PayRequestParam payRequestParam, Map<String, String> extraParams) throws Exception {
        Map<String, String> fieldMap = ReflectUtils.getObjFields(payRequestParam);
        EPayHttpRequest request = new EPayHttpRequest(EPayHttpRequest.METHOD.POST, "/mapi.php");
        request.addRequestParams(fieldMap);
        if (extraParams != null) {
            request.addRequestParams(extraParams);
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
