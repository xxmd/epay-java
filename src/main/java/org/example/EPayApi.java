package org.example;

import org.example.entity.PayRequestParam;
import org.example.entity.response.EApiPayResponse;
import org.example.entity.response.QueryOrdersResponse;
import org.example.request.HttpRequest;
import org.example.request.EPayHttpResponse;
import org.example.util.ReflectUtils;
import org.json.JSONObject;

import java.util.Map;
import java.util.function.Consumer;

public class EPayApi extends PayApi implements IEPayApi {
    public EPayApi(String pid, String key, String hostName) {
        super(pid, key, hostName);
    }

    @Override
    public EApiPayResponse apiInterfacePay(PayRequestParam payRequestParam) throws Exception {
        return apiInterfacePay(payRequestParam, null);
    }

    @Override
    public EApiPayResponse apiInterfacePay(PayRequestParam payRequestParam, Consumer<HttpRequest> requestModifier) throws Exception {
        Map<String, String> fieldMap = ReflectUtils.getObjFields(payRequestParam);
        HttpRequest request = new HttpRequest(HttpRequest.Method.POST, "/mapi.php");
        request.addRequestParams(fieldMap);
        if (requestModifier != null) {
            requestModifier.accept(request);
        }
        EPayHttpResponse response = httpClient.execute(request);
        String jsonStr = response.getDataAsString();
        EApiPayResponse apiPayResponse = new EApiPayResponse(new JSONObject(jsonStr));
        return apiPayResponse;
    }

    @Override
    public QueryOrdersResponse queryMultiplyOrder(int limit, int page) throws Exception {
        return null;
    }
}
