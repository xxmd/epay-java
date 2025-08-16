package org.example;

import org.example.entity.PayRequestParam;
import org.example.entity.enums.EPaySignType;
import org.example.entity.response.*;
import org.example.request.EPayHttpClient;
import org.example.request.EPayHttpInterceptor;
import org.example.request.EPayHttpResponse;
import org.example.request.HttpRequest;
import org.example.request.impl.interceptor.PayPidInterceptor;
import org.example.util.Md5Utils;
import org.example.util.ReflectUtils;
import org.example.util.StringUtils;
import org.example.util.UrlBuilder;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class PayApi implements IPayApi {
    // 商户id
    protected String pid;
    // 密钥
    protected String key;
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
        interceptorList.add(new EPayHttpInterceptor() {
            @Override
            public EPayHttpResponse intercept(HttpRequest request, Chain chain) throws Exception {
                request.addRequestParam("pid", pid);
                if (request.getMethod().equals(HttpRequest.Method.GET)) {
                    request.addRequestParam("key", key);
                }
                if (request.getMethod().equals(HttpRequest.Method.POST)) {
                    request.setContentType(HttpRequest.ContentType.X_WWW_FORM_URLENCODED);
                }
                return chain.proceed(request);
            }
        });
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
//        if (payRequestParam.getType() == null) {
//            throw new IllegalArgumentException("type[支付类型]不能为空");
//        }
//        if (StringUtils.isEmpty(payRequestParam.getOutTradeNo())) {
//            throw new IllegalArgumentException("outTradeNo[商户订单号]不能为空");
//        }
//        if (payRequestParam.getType() == null) {
//            throw new IllegalArgumentException("type[支付类型]不能为空");
//        }
//        if (StringUtils.isEmpty(payRequestParam.getNotifyUrl())) {
//            throw new IllegalArgumentException("notifyUrl[异步通知地址]不能为空");
//        }
//        BigDecimal money = payRequestParam.getMoney();
//        if (money == null) {
//            throw new IllegalArgumentException("money[商品金额]不能为空");
//        }
//        if (money.compareTo(BigDecimal.ZERO) < 0) {
//            throw new IllegalArgumentException("money[商品金额]不能小于0");
//        }
//        if (money.scale() > 2) {
//            throw new IllegalArgumentException("money[商品金额]最多支持两位小数");
//        }
        Map<String, String> fieldMap = ReflectUtils.getObjFields(payRequestParam);
        fieldMap.put("pid", pid);
        TreeMap<String, String> sortedParamMap = new TreeMap<>(fieldMap);
        String concatParamStr = UrlBuilder.concatParamMap(sortedParamMap, false);
        String signature = Md5Utils.md5(concatParamStr + key);
        sortedParamMap.put("sign", signature);
        sortedParamMap.put("sign_type", EPaySignType.MD5.getValue());
        String queryStr = UrlBuilder.concatParamMap(sortedParamMap, true);
        return String.format(Locale.US, "%s/%s?%s", baseUrl, "submit.php", queryStr);
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

    protected void signRequest(HttpRequest request) {
        TreeMap<String, String> treeMap = new TreeMap<>(request.getParams());
        String concatParamStr = UrlBuilder.concatParamMap(treeMap, false);
        String signature = Md5Utils.md5(concatParamStr + key);
        request.addRequestParam("sign", signature);
        request.addRequestParam("sign_type", "MD5");
    }
}
