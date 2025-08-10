package org.example;

import com.google.gson.Gson;
import junit.framework.Assert;
import junit.framework.TestCase;
import org.example.config.HeimaPayConfig;
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

public class EPayApiTest extends TestCase {
    private Gson gson;
    // 易支付接口
    private EPayApi ePayApi;
    // 商户配置信息
    private PayPlatformConfig platformConfig = new ZPayConfig();

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        gson = new Gson();
        ePayApi = new EPayApi(platformConfig.gePid(), platformConfig.getKey(), platformConfig.getHostname());
        if (platformConfig instanceof ZPayConfig) {
            ePayApi.addInterceptor(new ZPayInterceptor());
        }
    }

    class ZPayInterceptor implements EPayHttpInterceptor {
        @Override
        public EPayHttpResponse intercept(EPayHttpRequest request, Chain chain) throws Exception {
            request.addRequestParam("cid", ZPayConfig.ZPayChannel.ALI_PAY.getChannelId());
            return chain.proceed(request);
        }
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
        requestParam.setClientIp("188.253.115.35");
        return requestParam;
    }

    /**
     * 获取默认支付请求参数
     *
     * @return
     */
    private PayRequestParam getDefaultRequestParam() {
        return getDefaultRequestParam(EPayType.WX_PAY);
    }

    public void testPageRedirectPayNoExtraParams() throws Exception {
        PayRequestParam requestParam = getDefaultRequestParam(EPayType.WX_PAY);
        String redirectPayLink = ePayApi.pageRedirectPay(requestParam);
        System.out.println("redirectPayLink: " + redirectPayLink);
        Assert.assertNotNull(redirectPayLink);
    }

    public void testPageRedirectPayWithExtraParams() throws Exception {
        PayRequestParam requestParam = getDefaultRequestParam(EPayType.WX_PAY);
        Map<String, String> extraParams = new HashMap<>();
        extraParams.put("cid", ZPayConfig.ZPayChannel.ALI_PAY.getChannelId());
        String redirectPayLink = ePayApi.pageRedirectPay(requestParam, extraParams);
        System.out.println("redirectPayLink: " + redirectPayLink);
        Assert.assertNotNull(redirectPayLink);
    }

    public void testApiInterfacePayNoExtraParams() throws Exception {
        PayRequestParam requestParam = getDefaultRequestParam(EPayType.WX_PAY);
        ApiPayResponse apiPayResponse = ePayApi.apiInterfacePay(requestParam);
        System.out.println("apiPayResponse: " + gson.toJson(apiPayResponse));
        Assert.assertNotNull(apiPayResponse);
    }

    public void testApiInterfacePaWithExtraParams() throws Exception {
        PayRequestParam requestParam = getDefaultRequestParam(EPayType.WX_PAY);
        Map<String, String> extraParams = new HashMap<>();
        extraParams.put("cid", ZPayConfig.ZPayChannel.ALI_PAY.getChannelId());
        ApiPayResponse apiPayResponse = ePayApi.apiInterfacePay(requestParam);
        System.out.println("apiPayResponse: " + gson.toJson(apiPayResponse));
        Assert.assertNotNull(apiPayResponse);
    }

    public void testQueryMerchantInfo() {
    }

    public void testQuerySettleRecord() {
    }

    public void testQueryOrder() {
    }

    public void testQueryOrders() {
    }

    public void testRefund() {
    }
}