package cn.wowtw_backend.controller;

import cn.wowtw_backend.model.Order;
import cn.wowtw_backend.utils.Result;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConfig;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradePrecreateModel;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/alipay")
public class AlipayController {
    String privateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCQ7Y50eMjkEarV9sByn/skkUy02/eewNJcLLM3ezf9ZhXPu4jWdDy5amsEyxAgdjgrZVX+/h0+nniXNCjWOFPwjj9JXpQHqpgw/9TFi6k9ocucikxviWt8gryqF2uzat8//+Yq0Ne4tZpiTi1TXQ9lNCMEePVP/LB9D/9Trpsu6J6ZPbZZpyV0Pp8G0izCXeJo3q2AG0ZuG/0EuOxX5ty1GSY5T+3+QOfvsBKKeBXjGFRzY4CBjlDTs3v4SvU8bwKc8+nVEHeL9mndXWyEF5Y5kZX09nI3B3kOkoaZcoWZfCDB80HDn/oLsMT/xKL/KYEAJscufCjhfJ2MCYWDl4PdAgMBAAECggEAS7YerpAO+r6UDbUbcoZLEB5sASvM332CJ68ebQSNCm8TQ/OHl+kfdAm5IMbohNzdmSYWV+Du7DCjdPJFxyukBk55l122XY7sdOShhhXg8xacJQpGxhn3MhOmdgFIPXG+cVPBLWru10rf/S00FpHOIiOri4GZPhEXLi52R4Q3A7iu0v9vprQMoNwwha2VwV4XC7854Vxoi4nm+HaHUyzso2N29NToD1RaolKypGbJpRP68fndK6a/UMIhDeVuuotUvoOKdGCT47shwSFNZvR33OgZOZeHEP0Lp3waOKLnHhF+4B8HVBOhXhE/kTdp4ZhaUHgmtDzs5byKBaHoZetBbQKBgQDMGE1JAFzhtKzAMttJExAIlLFvuBlYfZi44LXIyALkDwLVXNmmc3k3SuZIiJzJc3C5VhB2DfCGW8RgpPbcRp01tIgL6+m3uSh/tCvP1XR4nLZaNT38HdIKRRMhv14znc6Jk5bfGdBKqG03pOyRrRg28ZwpimspVZLXwbU9e4U+BwKBgQC1ySkOJ4QZWGRcW5RIeqC0IxM3NjpmDhrCfyYzlo7yyq4lq43ZQ+g9KrYlgZDHkNG9gqpf3kulR7eU75uTYtaGHx2YRJNx1ynYJ1Te92g51EdBv32YXO/rR6+rZu2cthy9q2lsJMxNU0mr29W76EysstQ6eWGeC5HXa6b8yfH1+wKBgF+xZUs9KE+LbxQOxFJW5cWgMjG3l+qtuNW2RoWFghV8FFAF4ObybXtHWwHXS+wIfGnpuH4huwzEKidGMZjGYx+zLbapPkQuRJbb207vPlTu5XEWVaHIOZsnF/lSyNBaxWQsFykz6T2YCt10Lpd/GUNptVak1+nhfaA76saRC8AxAoGBAJEJ48DQVHxoYwKxarj9ZjTze0Rk9ukwPbeK/S7R5FHg1ltl6jI8FRKgfl2kZ6BicoXSuB7vXMmNU/L3zdglVuYB83nm1QFSW6eDkhqnqTfin8vcfdfP5viA5K9L2y9aucm29tD69yqG1lPRfVGEgZCUyfGxoOoZbw5CbSRp96x7AoGAG2FnQ0TLNTYi0ZkTexpu2QNAuOnbRjjFk4ZQP4/mlXKRGwRALwN/ccKCeuliqyZ8RL4NkaaylsLWc8MDnAAlEcRtRDiSMUuGmMct8o3hNqhy0zctBtCuZtJyodSxqSyMwU3lSmdCcIFUmZG/Uem5pSkRVMaDW2tVBpulRtZa6O4=";
    String alipayPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA2d7gL+fJBE8iuqBnLrVMilIPj5YiZ8S+oh3nTLG3VQqyGmJwzFlVS/4Pal8fC0wziYn7la8YOOdXFIHg3F4GjXJ8sVZ24J1ubYcgjTipRnJmy/h3idZjNy7tkp3x6AqbZbYrtztIv1RKgucPS1s/XtVG22pYjun7e+vGaQDnO9pP3OCDTl1G/oDAoQlVlhcDNU1jbxZREy3uTD/RRl4OC5uElg97qQd6pTbEYHHt7OyGHIDa1Wr1VaHz24EyOg3NlmmzCRqdwTZeZI2NVopfkvNtU8GYyR2qYdEMAQevU1GMDw6pi4g68ug8BCqxxPU1yem3IISP8d+qUODirC5ykwIDAQAB";
    AlipayConfig alipayConfig = new AlipayConfig();

    @PostMapping("/precreate")
    public Result precreate(@RequestBody Order createOrder) throws AlipayApiException {
        String phoneNumber = createOrder.getPhoneNumber();
        String inviteIdentifier = createOrder.getInviteIdentifier();
        log.info("{}请求生成订单，携带{}邀请码", phoneNumber, inviteIdentifier);
        alipayConfig.setServerUrl("https://openapi-sandbox.dl.alipaydev.com/gateway.do");
        alipayConfig.setAppId("9021000137686466");
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
        if (response.isSuccess()) {
            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");
            // sdk版本是"4.38.0.ALL"及以上,可以参考下面的示例获取诊断链接
            // String diagnosisUrl = DiagnosisUtils.getDiagnosisUrl(response);
            // System.out.println(diagnosisUrl);
        }
        return null;
    }
}
