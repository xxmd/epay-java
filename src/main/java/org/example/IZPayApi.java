package org.example;

import org.example.entity.PayRequestParam;
import org.example.entity.response.ZApiPayResponse;
import org.example.request.HttpRequest;

import java.util.function.Consumer;

public interface IZPayApi extends IPayApi {
    /**
     * API接口支付
     *
     * @param payRequestParam 支付请求参数
     * @return 支付地址（网站url|二维码|小程序url）
     */
    ZApiPayResponse apiInterfacePay(PayRequestParam payRequestParam) throws Exception;

    /**
     * API接口支付
     *
     * @param payRequestParam 支付请求参数
     * @param requestModifier 请求修改器
     * @return 支付地址（网站url|二维码|小程序url）
     */
    ZApiPayResponse apiInterfacePay(PayRequestParam payRequestParam, Consumer<HttpRequest> requestModifier) throws Exception;
}
