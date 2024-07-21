package cn.wowtw_backend.controller;

import cn.wowtw_backend.model.alipay.AlipayOrder;
import cn.wowtw_backend.service.AlipayService;
import cn.wowtw_backend.utils.Result;
import com.alipay.api.AlipayApiException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/alipay")
@AllArgsConstructor
public class AlipayController {
    private final AlipayService alipayService;

    // 预创订单
    @PostMapping("/preCreate")
    public Result preCreateOrder(@RequestBody AlipayOrder preCreateRequest) throws AlipayApiException {
        log.info("{}请求生成订单，携带{}邀请码", preCreateRequest.getPhoneNumber(), preCreateRequest.getInviteIdentifier());
        AlipayOrder preCreateResponse = alipayService.preCreateOrder(preCreateRequest);
        return new Result(1, "success create order" , preCreateResponse);
    }

    // 查询订单状态
    @PostMapping("/query")
    public Result queryAlipayResult(@RequestBody AlipayOrder alipayOrder) throws AlipayApiException {
        String outTradeNo = alipayOrder.getOutTradeNo();
        String tradeStatus = alipayService.queryAlipayResult(outTradeNo);
        return new Result(1, "success query trade status", tradeStatus);
//        if ("TRADE_SUCCESS".equals(tradeStatus)) {
//            return Result.success(tradeStatus);
//        } else {
//            alipayService.pollPayment(outTradeNo);
//            String pollResult = alipayService.getPollResult(outTradeNo);
//            return Result.success(pollResult);
//        }
    }

    // 取消交易
    @GetMapping("/cancel")
    public Result cancelPayment(String outTradeNo) throws AlipayApiException {
        alipayService.cancelPayment(outTradeNo);
        return new Result(0, "支付超时，订单已关闭", null);
    }
}
