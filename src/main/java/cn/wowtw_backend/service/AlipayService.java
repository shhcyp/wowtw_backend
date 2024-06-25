package cn.wowtw_backend.service;

import cn.wowtw_backend.model.AlipayOrder;
import cn.wowtw_backend.model.PreCreateResponse;
import com.alipay.api.AlipayApiException;

public interface AlipayService {
    // 预创订单生
    AlipayOrder preCreateOrder(AlipayOrder preCreateRequest) throws AlipayApiException;

    // 查询交易状态
    String queryAlipayResult(String outTradeNo) throws AlipayApiException;

    // 支付状态轮询
    void pollPayment(String outTradeNo);

    // 取消交易
    void cancelPayment(String outTradeNo) throws AlipayApiException;

    String getPollResult(String outTradeNo);
}
