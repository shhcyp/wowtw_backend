package cn.wowtw_backend.controller;

import cn.wowtw_backend.model.Order;
import cn.wowtw_backend.utils.QRCodeUtil;
import cn.wowtw_backend.utils.Result;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConfig;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradePrecreateModel;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.zxing.WriterException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/alipay")
public class AlipayController {
    String privateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCDi65XrrlOXUYgpJRZaHN5x0fiPCFnLmxxVqyOtzW3g4+wuvmPqKNZx9LzwdZIs3XV79YCa8LCD0oeShYnVQyUtS/j2ECNNhcXhZMNQ9h15kr5ZVQq+nLP2/1ou/BsAZZd/817OGy0VCtQuK32O5HvAAEqeBltVKAtznefJGyRrEiXYu2NB+oH8e9+gGT3rc4CBPcc7Qh5p/lTdEnIXvTIe/hnwa9hzKmGK6iSMWJVDpA6jJkR1ECkPjTTFUc9RRzqFxQIgeL7ieiABYCqJBKO8Z4sXWSD4cJ49OpjI2J/Q6OsTSq7lQ74gDsc+RRetiRKEiPOby1mI1gWdNuvCSlrAgMBAAECggEAb+vYWiH+5/3UPNHKpoeIOISlVId0pCWLfqMQDh0zRwBYsTVkQCZrVWf+xAaYGyMVjwCQRV8KRbJcjYjbcHucSKogJOtJgNwUqZ6sjwy6jxVxlN/xzN9p8BBsez/sPvjaxv9uBPjtBEDY2cVHvcjRUVffphKpD6yA3Hherrh+5zFFLKUTkAreU9IM+sJv/KtliTL7YOpZdzq/fF+NSQnkDM4pKchQRZ90KtBAW7BV4Yi8fTxqcQdZrR5UIyxTP7RrFsZAXnXSdzigX/yYXkWJ+g0RwTmYOH9hSIU9HYBwquVrKfMu87RVQKbjMhUowNZ/EByIrZTi0d17qyUORngegQKBgQDFxmp2VFnDeCp55fvrakmob1+0Fmba+kVn4Y9Mpr5Otd3rXaXir7a+JFboJmOLUhpnKshJpSzOmw4/7Fh9qUZNppnsQcFl6yxqKXFfYGC8ymF/j9UpASBFd7IM+NuEZIOnW7ZR2TWPaUJncfJev3oNUo8G8OsS3TsTxsuJuiYgjQKBgQCqRctvaVEDh0GdpRTX4EXMcpQy2M6h/oVbM1Aeg+GGdIa8yYhS6YwkMPYfpBHH8bKSX1qkktgGeSxsxwBplk+QQk6yhCu7hDsBybNid/o+VjJ69KEXXCfX6MUUWVK/a6wfzDnh41AawLI9VR2m5yc6aqP7P6qqxlEHYAoFSY3f1wKBgGAH/f3C62scWBOkdnRSwb1FhwTcs8i0KStNvNUNSe7SJBV6/WdaxrMA1ioXKdKmZSefAG2yBFW1uG+VhUpO/VILCUZMj2m7jW9m/CPBmndRFZJo9qnYYCEbA5uE4qIgZETJJXgMrhMamyG7lGIpO0xowAYwyRP51AvARWh0uLqpAoGAXzqLrXK8UXna87z47EdNIivOwKVK44TCtp/7JiXiAt224WzhJmXh7/fGHyoWm7mMgpp7ymEqGmy4sNmXXHYpZ2f+NY8AGm64xaY2AlNRjH2O2/9m/PaYfgN7/YZ7Zf1lFZ+Vm8US7vNofC7XV5od8Wjk70bLP5mMhdBrqktZ4p8CgYEAjiVHvgyRTQOe/D7K5NIrcsifzXwbfrV0y36u2MMRPWr+9DdWrfnxpUYkkcPBYZYidEtH9LSb/yjmktnAwSXeqO4+1mXDUmg5GDDbtePmQU2/cZu+1zJF5ogN+37UCB3gJYtmbB5N2PxOCkxanO1Yaj4b6L1TTBwgrOflZHdKsLs=";
    String alipayPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA4gGimZ8dqRiPbx1vcg0nKeA/QeGGLnmmOfhNXq7bomSwuz2KPcTjmHQExG1HTbtrp6YL1t+lu8cQbPue2WS7GZAhwTwsFWzm7QCWzgHVk0ty00GSTxNufeA/bPXcTBELMoAANRUYiUl9Ccl1cV8IARbmA9r8HL05su/9pbDpWURYvlCTVIvUR5Avievd5S88H4y5QY7N7CShLKmEOQhIDXxlsVMRYv/q4sqMcXmDqoWRuF33om68LbW1rBeRMh0ePFMyBSsBBhmn/tshkiv6d8RlTj0LenrBE+7DF+IAP2aYqFgn/wGnDiqx6/dwn5p9IImNbmmR1SyJc4EbEIzrTwIDAQAB";
    AlipayConfig alipayConfig = new AlipayConfig();

    @PostMapping("/precreate")
    public Result precreate(@RequestBody Order createOrder) throws AlipayApiException {
        Map<String, Object> responseMap = new HashMap<>();
        String phoneNumber = createOrder.getPhoneNumber();
        String inviteIdentifier = createOrder.getInviteIdentifier();
        log.info("{}请求生成订单，携带{}邀请码", phoneNumber, inviteIdentifier);
        alipayConfig.setServerUrl("https://openapi.alipay.com/gateway.do");
        alipayConfig.setAppId("2021004148629471");
        alipayConfig.setPrivateKey(privateKey);
        alipayConfig.setFormat("json");
        alipayConfig.setAlipayPublicKey(alipayPublicKey);
        alipayConfig.setCharset("UTF-8");
        alipayConfig.setSignType("RSA2");
        AlipayClient alipayClient = new DefaultAlipayClient(alipayConfig);
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
        AlipayTradePrecreateModel model = new AlipayTradePrecreateModel();
        model.setOutTradeNo("20150320010101001");
        model.setTotalAmount("88.88");
        model.setSubject("Iphone6 16G");
        request.setBizModel(model);
        AlipayTradePrecreateResponse response = alipayClient.execute(request);
        System.out.println(response.getBody());

        String httpBody = response.getBody();
        JsonObject jsonObject = JsonParser.parseString(httpBody).getAsJsonObject();
        String alipayQRCode = jsonObject.getAsJsonObject("alipay_trade_precreate_response").get("qr_code").getAsString();
        System.out.println(alipayQRCode);
        if (response.isSuccess()) {
            System.out.println("调用成功");
//            String filePath = "/Users/cyp/Desktop/qrCode/qrcode.png";
//            try {
//                QRCodeUtil.generateQRCodeImage(alipayQRCode, 350, 350, filePath);
//                responseMap.put("qrCodePath", filePath);
//                responseMap.put("success", true);
//            } catch (WriterException | IOException e) {
//                throw new RuntimeException(e);
//            }
        } else {
            System.out.println("调用失败");
            // sdk版本是"4.38.0.ALL"及以上,可以参考下面的示例获取诊断链接
            // String diagnosisUrl = DiagnosisUtils.getDiagnosisUrl(response);
            // System.out.println(diagnosisUrl);
        }
        return new Result(1, "success get qrcode link" , alipayQRCode);
    }
}
