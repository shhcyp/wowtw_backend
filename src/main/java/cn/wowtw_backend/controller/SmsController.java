package cn.wowtw_backend.controller;

import cn.wowtw_backend.utils.Result;
import cn.wowtw_backend.model.sms.SmsVerification;
import cn.wowtw_backend.service.impl.SmsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api")
public class SmsController {

    private final SmsServiceImpl smsService;

    public SmsController(SmsServiceImpl smsService) {
        this.smsService = smsService;
    }

    @PostMapping("/sms/send")
    public Result sendCode(@RequestBody Map<String, String> payload) {
        String phoneNumber = payload.get("phoneNumber");
        log.info("=={}==请求注册码", phoneNumber);
        boolean success = smsService.sendVerificationCode(phoneNumber);
        return success ? Result.success("验证码发送成功") : Result.fail("发送验证码失败");
    }

    @PostMapping("/sms/verify")
    public Result verifyCode(@RequestBody SmsVerification verify) {
        String phoneNumber = verify.getPhoneNumber();
        String code = verify.getVerificationCode();
        boolean isValid = smsService.verifyCode(phoneNumber, code);
        log.info(isValid ? "验证通过" : "验证码错误");
        return isValid ? Result.success("验证成功") : Result.fail("验证码错误");
    }
}
