package org.example;

import junit.framework.Assert;
import junit.framework.TestCase;
import org.example.config.ZPayConfig;
import org.example.entity.PayRequestParam;
import org.example.entity.enums.PayType;
import org.example.entity.response.PayResponse;
import org.example.entity.response.QueryOrderResponse;
import org.example.entity.response.ZApiPayResponse;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ZPayApiTest extends TestCase {
    // ZPay接口
    private ZPayApi zPayApi;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        ZPayConfig zPayConfig = new ZPayConfig();
        this.zPayApi = new ZPayApi(zPayConfig.gePid(), zPayConfig.getKey(), zPayConfig.getHostname(), ZPayConfig.ZPayChannel.ALI_PAY.getChannelId());
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
        requestParam.setMoney(new BigDecimal("1.233"));
        return requestParam;
    }

    public void testPageRedirectPay() throws Exception {
        PayRequestParam requestParam = getDefaultRequestParam(PayType.ALI_PAY);
        String redirectPayLink = zPayApi.pageRedirectPay(requestParam);
        System.out.println("redirectPayLink: " + redirectPayLink);
        Assert.assertNotNull(redirectPayLink);
    }

    public void testApiInterfacePay() throws Exception {
        PayRequestParam requestParam = getDefaultRequestParam(PayType.ALI_PAY);
        ZApiPayResponse apiPayResponse = zPayApi.apiInterfacePay(requestParam);
        System.out.println("apiPayResponse: " + apiPayResponse);
        Assert.assertNotNull(apiPayResponse);
    }

    public void testQuerySingleOrder() throws Exception {
        QueryOrderResponse response = zPayApi.querySingleOrder("20250816133402261");
        System.out.printf("response: " + response);
        Assert.assertNotNull(response);
    }

    public void testRefund() throws Exception {
        PayResponse response = zPayApi.refund(null, "20250816133402261", new BigDecimal("1.23"));
        System.out.printf("response: " + response);
        Assert.assertNotNull(response);
    }
}