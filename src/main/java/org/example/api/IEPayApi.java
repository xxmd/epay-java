package org.example.api;

import org.example.entity.PayRequestParam;
import org.example.entity.response.*;

public interface IEPayApi extends IBasePayApi {
    /**
     * API接口支付
     *
     * @param payRequestParam 支付请求参数
     * @return 支付地址（网站url|二维码|小程序url）
     * @throws Exception API接口支付异常
     */
    EApiPayResponse apiInterfacePay(PayRequestParam payRequestParam) throws Exception;

    /**
     * 查询商户信息
     *
     * @return 商户信息
     * @throws Exception 查询商户信息异常
     */
    QueryMerchantResponse queryMerchantInfo() throws Exception;

    /**
     * 查询结算记录
     *
     * @return 结算记录
     * @throws Exception 查询结算记录异常
     */
    QuerySettleResponse querySettleRecord() throws Exception;

    /**
     * 查询多个订单
     *
     * @param limit 查询订单数量
     * @param page  页码
     * @return 结算记录
     * @throws Exception 查询多个订单异常
     */
    QueryOrdersResponse queryMultiplyOrder(int limit, int page) throws Exception;
}
