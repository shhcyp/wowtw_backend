package cn.wowtw_backend.controller;

import cn.wowtw_backend.model.Order;
import cn.wowtw_backend.service.AlipayService;
import cn.wowtw_backend.utils.Result;
import com.alipay.api.AlipayApiException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/alipay")
@AllArgsConstructor
public class AlipayController {
    private final AlipayService alipayService;
    @PostMapping("/preCreate")
    public Result preCreate(@RequestBody Order createOrder) throws AlipayApiException {
        log.info("{}请求生成订单，携带{}邀请码", createOrder.getPhoneNumber(), createOrder.getInviteIdentifier());
        String alipayQRCode = alipayService.preCreate(createOrder);
        return new Result(1, "success get qrcode link" , alipayQRCode);
    }

    @PostMapping("/query")
    public Result queryAlipayResult(@RequestParam String outTradeNo) throws AlipayApiException {
        String tradeStatus = alipayService.queryAlipayResult(outTradeNo);
        return Result.success(tradeStatus);
    }
}
