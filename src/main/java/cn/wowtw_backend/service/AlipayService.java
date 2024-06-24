package cn.wowtw_backend.service;

import cn.wowtw_backend.model.Order;
import com.alipay.api.AlipayApiException;

import java.util.Map;

public interface AlipayService {
    // 预创订单生成二维码
    String preCreate(Order createOrder) throws AlipayApiException;

    // 主动查询交易结果
    String queryAlipayResult(String outTradeNo) throws AlipayApiException;
}
