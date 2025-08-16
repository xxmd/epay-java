package org.example;

import junit.framework.Assert;
import junit.framework.TestCase;
import org.example.config.PayPlatformConfig;
import org.example.config.ZPayConfig;
import org.example.entity.PayRequestParam;
import org.example.entity.enums.PayType;
import org.example.entity.response.EApiPayResponse;
import org.example.request.EPayHttpInterceptor;
import org.example.request.HttpRequest;
import org.example.request.EPayHttpResponse;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class EPayApiTest extends TestCase {
    // 易支付接口
    private EPayApi ePayApi;
    // 商户配置信息
    private PayPlatformConfig platformConfig = new ZPayConfig();

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        ePayApi = new EPayApi(platformConfig.gePid(), platformConfig.getKey(), platformConfig.getHostname());
        if (platformConfig instanceof ZPayConfig) {
            ePayApi.addInterceptor(new ZPayInterceptor());
        }
    }

    class ZPayInterceptor implements EPayHttpInterceptor {
        @Override
        public EPayHttpResponse intercept(HttpRequest request, Chain chain) throws Exception {
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
    private PayRequestParam getDefaultRequestParam(PayType ePayType) {
        PayRequestParam requestParam = new PayRequestParam();
        requestParam.setType(ePayType);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String now = sdf.format(new Date());
        requestParam.setOutTradeNo(now);
        requestParam.setNotifyUrl("https://ftq.ink/download");
        requestParam.setReturnUrl("https://ftq.ink/download");
        requestParam.setName("测试商品");
        requestParam.setMoney(new BigDecimal("1.23"));
        requestParam.setClientIp("188.253.115.12");
        return requestParam;
    }

    /**
     * 获取默认支付请求参数
     *
     * @return
     */
    private PayRequestParam getDefaultRequestParam() {
        return getDefaultRequestParam(PayType.WX_PAY);
    }

    public void testPageRedirectPayNoExtraParams() throws Exception {
        PayRequestParam requestParam = getDefaultRequestParam(PayType.WX_PAY);
        String redirectPayLink = ePayApi.pageRedirectPay(requestParam);
        System.out.println("redirectPayLink: " + redirectPayLink);
        Assert.assertNotNull(redirectPayLink);
    }

    public void testPageRedirectPayWithExtraParams() throws Exception {
        PayRequestParam requestParam = getDefaultRequestParam(PayType.WX_PAY);
        Map<String, String> extraParams = new HashMap<>();
        extraParams.put("cid", ZPayConfig.ZPayChannel.ALI_PAY.getChannelId());
        String redirectPayLink = ePayApi.pageRedirectPay(requestParam, extraParams);
        System.out.println("redirectPayLink: " + redirectPayLink);
        Assert.assertNotNull(redirectPayLink);
    }

    public void testApiInterfacePayNoModifier() throws Exception {
        PayRequestParam requestParam = getDefaultRequestParam(PayType.ALI_PAY);
        EApiPayResponse apiPayResponse = ePayApi.apiInterfacePay(requestParam);
        Assert.assertNotNull(apiPayResponse);
    }

    public void testApiInterfacePaWithModifier() throws Exception {
        PayRequestParam requestParam = getDefaultRequestParam(PayType.WX_PAY);
        Map<String, String> extraParams = new HashMap<>();
        extraParams.put("cid", ZPayConfig.ZPayChannel.ALI_PAY.getChannelId());
        EApiPayResponse apiPayResponse = ePayApi.apiInterfacePay(requestParam);
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