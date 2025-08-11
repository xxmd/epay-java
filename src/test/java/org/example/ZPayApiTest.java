package org.example;

import com.google.gson.Gson;
import junit.framework.Assert;
import junit.framework.TestCase;
import org.example.config.PayPlatformConfig;
import org.example.config.ZPayConfig;
import org.example.entity.PayRequestParam;
import org.example.entity.enums.EPayType;
import org.example.entity.response.ApiPayResponse;
import org.example.request.EPayHttpInterceptor;
import org.example.request.EPayHttpRequest;
import org.example.request.EPayHttpResponse;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ZPayApiTest extends TestCase {
    private Gson gson;
    // 易支付接口
    private EPayApi ePayApi;

    public static final String pid = "2025071018032699";
    public static final String key = "2025071018032699";
    public static final String hostname = "2025071018032699";
    public static final String cid = "2025071018032699";

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        this.gson = new Gson();
        ZPayConfig zPayConfig = new ZPayConfig();
        this.ePayApi = new ZPayApi(zPayConfig.gePid(), zPayConfig.getKey(), zPayConfig.getHostname(), ZPayConfig.ZPayChannel.ALI_PAY.getChannelId());
    }

    /**
     * 获取默认支付请求参数
     *
     * @param ePayType
     * @return
     */
    private PayRequestParam getDefaultRequestParam(EPayType ePayType) {
        PayRequestParam requestParam = new PayRequestParam();
        requestParam.setType(ePayType);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String now = sdf.format(new Date());
        requestParam.setOutTradeNo(now);
        requestParam.setNotifyUrl("https://ftq.ink/download");
        requestParam.setReturnUrl("https://ftq.ink/download");
        requestParam.setName("测试商品");
        requestParam.setMoney(new BigDecimal("1.23"));
        return requestParam;
    }

    public void testPageRedirectPay() throws Exception {
        PayRequestParam requestParam = getDefaultRequestParam(EPayType.ALI_PAY);
        String redirectPayLink = ePayApi.pageRedirectPay(requestParam);
        System.out.println("redirectPayLink: " + redirectPayLink);
        Assert.assertNotNull(redirectPayLink);
    }

    public void testApiInterfacePay() throws Exception {
        PayRequestParam requestParam = getDefaultRequestParam(EPayType.ALI_PAY);
        requestParam.setClientIp("188.253.115.35");
        ApiPayResponse apiPayResponse = ePayApi.apiInterfacePay(requestParam);
        System.out.println("apiPayResponse: " + gson.toJson(apiPayResponse));
        Assert.assertNotNull(apiPayResponse);
    }
}