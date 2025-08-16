package org.example;

import org.example.entity.PayRequestParam;
import org.example.entity.enums.EPaySignType;
import org.example.entity.response.*;
import org.example.request.EPayHttpClient;
import org.example.request.EPayHttpInterceptor;
import org.example.request.EPayHttpResponse;
import org.example.request.HttpRequest;
import org.example.request.impl.interceptor.EPayPidInterceptor;
import org.example.request.impl.interceptor.EPaySignatureInterceptor;
import org.example.util.Md5Utils;
import org.example.util.ReflectUtils;
import org.example.util.StringUtils;
import org.example.util.UrlBuilder;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.*;

public class PayApi implements IPayApi {
    // 商户id
    private String pid;
    // 密钥
    private String key;
    // 基础链接
    private String baseUrl;
    // http请求客户端
    protected EPayHttpClient httpClient;
    // 固定拦截器数量
    private int fixedInterceptorSize;

    public PayApi(String pid, String key, String hostName) {
        this.pid = pid;
        this.key = key;
        this.baseUrl = "https://" + hostName;
        initHttpClient();
    }

    private void initHttpClient() {
        httpClient = new EPayHttpClient(this.baseUrl);
        List<EPayHttpInterceptor> interceptorList = new ArrayList<>();
        interceptorList.add(new EPayPidInterceptor(pid));
//        interceptorList.add(new EPaySignatureInterceptor(key, EPaySignType.MD5));
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
    public QueryMerchantResponse queryMerchantInfo(String pid, String key) {
        return null;
    }

    @Override
    public QuerySettleResponse querySettleRecord(String tradeNo, String outTradeNo) {
        return null;
    }

    @Override
    public QueryOrderResponse querySingleOrder(String outTradeNo) throws Exception {
        if (StringUtils.isEmpty(outTradeNo)) {
            throw new IllegalArgumentException("outTradeNo[商户订单号]不能为空");
        }
        HttpRequest httpRequest = new HttpRequest(HttpRequest.Method.GET, "/api.php");
        httpRequest.addRequestParam("act", "order");
        httpRequest.addRequestParam("pid", pid);
        httpRequest.addRequestParam("key", key);
        httpRequest.addRequestParam("out_trade_no", outTradeNo);
        EPayHttpResponse response = httpClient.execute(httpRequest);
        JSONObject jsonObject = new JSONObject(response.getDataAsString());
        QueryOrderResponse queryOrderResponse = new QueryOrderResponse(jsonObject);
        return queryOrderResponse;
    }

    @Override
    public PayResponse refund(String tradeNo, String outTradeNo, BigDecimal money) throws Exception {
        if (StringUtils.isEmpty(tradeNo) && StringUtils.isEmpty(outTradeNo)) {
            throw new IllegalArgumentException("tradeNo[易支付订单号]和outTradeNo[商户订单号]不能同时为空");
        }
        HttpRequest httpRequest = new HttpRequest(HttpRequest.Method.POST, "/api.php?act=refund");
        httpRequest.addRequestParam("pid", pid);
        httpRequest.addRequestParam("key", key);
        httpRequest.addRequestParam("trade_no", tradeNo);
        httpRequest.addRequestParam("out_trade_no", outTradeNo);
        httpRequest.addRequestParam("money", money.toString());
        EPayHttpResponse response = httpClient.execute(httpRequest);
        PayResponse payResponse = new PayResponse(response.getDataAsJsonObject());
        return payResponse;
    }
}
