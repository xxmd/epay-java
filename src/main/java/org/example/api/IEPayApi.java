package org.example.api;

import org.example.entity.PayRequestParam;
import org.example.entity.response.*;

public interface IEPayApi extends IBasePayApi {
    /**
     * API接口支付
     *
     * @param payRequestParam 支付请求参数
     * @return 支付地址（网站url|二维码|小程序url）
     */
    EApiPayResponse apiInterfacePay(PayRequestParam payRequestParam) throws Exception;

    /**
     * 查询商户信息
     *
     * @return 商户信息
     */
    QueryMerchantResponse queryMerchantInfo() throws Exception;

    /**
     * 查询结算记录
     *
     * @return 结算记录
     */
    QuerySettleResponse querySettleRecord() throws Exception;

    /**
     * 查询多个订单
     *
     * @param limit 查询订单数量
     * @param page  页码
     * @return 结算记录
     */
    QueryOrdersResponse queryMultiplyOrder(int limit, int page) throws Exception;
}
