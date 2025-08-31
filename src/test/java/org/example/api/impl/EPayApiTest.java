package org.example.api.impl;

import junit.framework.Assert;
import junit.framework.TestCase;
import org.example.api.impl.config.HeimaPayConfig;
import org.example.api.impl.config.PayPlatformConfig;
import org.example.entity.PayRequestParam;
import org.example.entity.enums.PayType;
import org.example.entity.response.*;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EPayApiTest extends TestCase {
    // 易支付接口
    private EPayApi ePayApi;
    // 商户配置信息
    private PayPlatformConfig platformConfig = new HeimaPayConfig();

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        this.ePayApi = new EPayApi(platformConfig.gePid(), platformConfig.getKey(), platformConfig.getHostname());
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

    public void testPageRedirectPay() throws Exception {
        PayRequestParam requestParam = getDefaultRequestParam(PayType.ALI_PAY);
        String redirectPayLink = ePayApi.pageRedirectPay(requestParam);
        System.out.println("redirectPayLink: " + redirectPayLink);
        Assert.assertNotNull(redirectPayLink);
    }

    public void testApiInterfacePay() throws Exception {
        PayRequestParam requestParam = getDefaultRequestParam(PayType.ALI_PAY);
        EApiPayResponse apiPayResponse = ePayApi.apiInterfacePay(requestParam);
        System.out.println("apiPayResponse: " + apiPayResponse);
        Assert.assertNotNull(apiPayResponse);
    }

    public void testQueryMerchantInfo() throws Exception {
        QueryMerchantResponse response = ePayApi.queryMerchantInfo();
        System.out.println("response: " + response);
        Assert.assertNotNull(response);
    }

    public void testQuerySettleRecord() throws Exception {
        QuerySettleResponse response = ePayApi.querySettleRecord();
        System.out.println("response: " + response);
        Assert.assertNotNull(response);
    }

    public void testQuerySingleOrder() throws Exception {
        QueryOrderResponse response = ePayApi.querySingleOrder("20250831134255843");
        System.out.println("response: " + response);
        Assert.assertNotNull(response);
    }

    public void testQueryOrders() throws Exception {
        QueryOrdersResponse response = ePayApi.queryMultiplyOrder(20, 1);
        System.out.println("response: " + response);
        Assert.assertNotNull(response);
    }

    public void testRefund() throws Exception {
        PayResponse response = ePayApi.refund(null, "20250816153119810", new BigDecimal("1.234"));
        System.out.println("response: " + response);
        Assert.assertNotNull(response);
    }
}