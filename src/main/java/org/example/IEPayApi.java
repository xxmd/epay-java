package org.example;

import org.example.entity.PayRequestParam;
import org.example.entity.response.*;
import org.example.request.EPayHttpInterceptor;
import org.example.request.EPayHttpRequest;

import java.math.BigDecimal;
import java.util.Map;
import java.util.function.Consumer;

public interface IEPayApi {
    void addInterceptor(EPayHttpInterceptor interceptor);

    /**
     * 页面跳转支付
     *
     * @param payRequestParam 支付请求参数
     * @return 跳转链接
     */
    String pageRedirectPay(PayRequestParam payRequestParam) throws Exception;

    /**
     * 页面跳转支付
     *
     * @param payRequestParam 支付请求参数
     * @param extraParams     额外参数
     * @return 跳转链接
     */
    String pageRedirectPay(PayRequestParam payRequestParam, Map<String, String> extraParams) throws Exception;

    /**
     * API接口支付
     *
     * @param payRequestParam 支付请求参数
     * @return 支付地址（网站url|二维码|小程序url）
     */
    ApiPayResponse apiInterfacePay(PayRequestParam payRequestParam) throws Exception;

    /**
     * API接口支付
     *
     * @param payRequestParam 支付请求参数
     * @param requestModifier 请求修改器
     * @return 支付地址（网站url|二维码|小程序url）
     */
    ApiPayResponse apiInterfacePay(PayRequestParam payRequestParam, Consumer<EPayHttpRequest> requestModifier) throws Exception;

    /**
     * 查询商户信息
     *
     * @param pid 商户ID
     * @param key 商户密钥
     * @return 商户信息
     */
    QueryMerchantResponse queryMerchantInfo(String pid, String key) throws Exception;

    /**
     * 查询结算记录
     *
     * @param pid 商户ID
     * @param key 商户密钥
     * @return 结算记录
     */
    QuerySettleResponse querySettleRecord(String pid, String key) throws Exception;

    /**
     * 查询单个订单
     * 系统订单号 和 商户订单号 二选一传入即可，如果都传入以系统订单号为准！
     *
     * @param tradeNo    系统订单号
     * @param outTradeNo 商户订单号
     * @return 结算记录
     */
    QueryOrderResponse queryOrder(String tradeNo, String outTradeNo) throws Exception;

    /**
     * 批量查询订单
     *
     * @param limit 查询订单数量
     * @param page  页码
     * @return 结算记录
     */
    QueryOrdersResponse queryOrders(int limit, int page) throws Exception;


    /**
     * 退款
     *
     * @param tradeNo    系统订单号
     * @param outTradeNo 商户订单号
     * @param money      退款金额
     * @return
     */
    EPayResponse refund(String tradeNo, String outTradeNo, BigDecimal money) throws Exception;
}
