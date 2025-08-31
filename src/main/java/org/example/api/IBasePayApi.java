package org.example.api;

import org.example.entity.PayRequestParam;
import org.example.entity.response.*;
import org.example.request.EPayHttpInterceptor;

import java.math.BigDecimal;

public interface IBasePayApi {
    void addInterceptor(EPayHttpInterceptor interceptor);

    /**
     * 页面跳转支付
     *
     * @param payRequestParam 支付请求参数
     * @return 跳转链接
     */
    String pageRedirectPay(PayRequestParam payRequestParam) throws Exception;


    /**
     * 查询单个订单
     * 系统订单号 和 商户订单号 二选一传入即可，如果都传入以系统订单号为准！
     *
     * @param outTradeNo 商户订单号
     * @return 结算记录
     */
    QueryOrderResponse querySingleOrder(String outTradeNo) throws Exception;


    /**
     * 退款
     *
     * @param tradeNo    系统订单号
     * @param outTradeNo 商户订单号
     * @param money      退款金额
     * @return
     */
    PayResponse refund(String tradeNo, String outTradeNo, BigDecimal money) throws Exception;
}
