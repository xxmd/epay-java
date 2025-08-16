package org.example;

import org.example.entity.PayRequestParam;
import org.example.entity.response.EApiPayResponse;
import org.example.entity.response.QueryMerchantResponse;
import org.example.entity.response.QueryOrdersResponse;
import org.example.entity.response.QuerySettleResponse;
import org.example.request.EPayHttpResponse;
import org.example.request.HttpRequest;
import org.example.request.impl.interceptor.PayPidInterceptor;
import org.example.util.ReflectUtils;

import java.util.Map;

public class EPayApi extends PayApi implements IEPayApi {
    public EPayApi(String pid, String key, String hostName) {
        super(pid, key, hostName);
        addInterceptor(new PayPidInterceptor(pid));
    }

    @Override
    public EApiPayResponse apiInterfacePay(PayRequestParam payRequestParam) throws Exception {
        Map<String, String> fieldMap = ReflectUtils.getObjFields(payRequestParam);
        HttpRequest request = new HttpRequest(HttpRequest.Method.POST, "/mapi.php");
        request.addRequestParams(fieldMap);
        request.addRequestParam("pid", pid);
        signRequest(request);
        EPayHttpResponse response = httpClient.execute(request);
        EApiPayResponse apiPayResponse = new EApiPayResponse(response.getDataAsJsonObject());
        return apiPayResponse;
    }

    @Override
    public QueryMerchantResponse queryMerchantInfo() throws Exception {
        HttpRequest request = new HttpRequest(HttpRequest.Method.GET, "/api.php");
        request.addRequestParam("act", "query");
        EPayHttpResponse response = httpClient.execute(request);
        QueryMerchantResponse queryMerchantResponse = new QueryMerchantResponse(response.getDataAsJsonObject());
        return queryMerchantResponse;
    }

    @Override
    public QuerySettleResponse querySettleRecord() throws Exception {
        HttpRequest request = new HttpRequest(HttpRequest.Method.GET, "/api.php");
        request.addRequestParam("act", "settle");
        EPayHttpResponse response = httpClient.execute(request);
        QuerySettleResponse querySettleResponse = new QuerySettleResponse(response.getDataAsJsonObject());
        return querySettleResponse;
    }

    @Override
    public QueryOrdersResponse queryMultiplyOrder(int limit, int page) throws Exception {
        HttpRequest request = new HttpRequest(HttpRequest.Method.GET, "/api.php");
        request.addRequestParam("act", "orders");
        request.addRequestParam("limit", limit);
        request.addRequestParam("page", page);
        EPayHttpResponse response = httpClient.execute(request);
        QueryOrdersResponse queryOrdersResponse = new QueryOrdersResponse(response.getDataAsJsonObject());
        return queryOrdersResponse;
    }
}
