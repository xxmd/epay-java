package org.example;

import org.example.entity.PayRequestParam;
import org.example.entity.response.*;
import org.example.request.EPayHttpInterceptor;
import org.example.request.HttpRequest;
import org.example.request.EPayHttpResponse;
import org.example.util.Md5Utils;
import org.example.util.ReflectUtils;
import org.json.JSONObject;

import java.util.Map;
import java.util.TreeMap;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * ZPay支付接口
 */
public class ZPayApi extends PayApi implements IZPayApi {
    // 支付渠道id
    private String channelId;

    public ZPayApi(String pid, String key, String hostName, String channelId) {
        super(pid, key, hostName);
        this.channelId = channelId;
    }

    @Override
    public ZApiPayResponse apiInterfacePay(PayRequestParam payRequestParam) throws Exception {
        HttpRequest request = new HttpRequest(HttpRequest.Method.POST, "/mapi.php");
        Map<String, String> fieldMap = ReflectUtils.getObjFields(payRequestParam);
        request.addRequestParams(fieldMap);
        request.addRequestParam("pid", pid);
        request.addRequestParam("cid", channelId);
        signRequest(request);
        EPayHttpResponse response = httpClient.execute(request);
        ZApiPayResponse apiPayResponse = new ZApiPayResponse(response.getDataAsJsonObject());
        return apiPayResponse;
    }
}
