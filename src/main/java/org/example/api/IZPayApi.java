package org.example.api;

import org.example.entity.PayRequestParam;
import org.example.entity.response.ZApiPayResponse;

public interface IZPayApi extends IBasePayApi {
    /**
     * API接口支付
     *
     * @param payRequestParam 支付请求参数
     * @return 支付地址（网站url|二维码|小程序url）
     * @throws Exception API接口支付异常
     */
    ZApiPayResponse apiInterfacePay(PayRequestParam payRequestParam) throws Exception;
}
