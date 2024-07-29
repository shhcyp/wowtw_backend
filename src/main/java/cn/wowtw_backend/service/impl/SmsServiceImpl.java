package cn.wowtw_backend.service.impl;

import cn.wowtw_backend.model.sms.SmsVerification;
import cn.wowtw_backend.utils.SmsUtil;
import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.teautil.models.RuntimeOptions;
import cn.wowtw_backend.repository.SmsVerificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class SmsServiceImpl {

    private final SmsVerificationRepository smsVerificationRepository;
    private final Client smsClient;

    @Autowired
    public SmsServiceImpl(Client smsClient, SmsVerificationRepository smsVerificationRepository) {
        this.smsClient = smsClient;
        this.smsVerificationRepository = smsVerificationRepository;
    }

    public boolean sendVerificationCode(String phoneNumber) {
        try {
            String code = SmsUtil.generateCode();
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime validUntil = now.plusMinutes(5);

            Optional<SmsVerification> existingRecordOptional = smsVerificationRepository.findByPhoneNumber(phoneNumber);
            if (existingRecordOptional.isPresent()) {
                SmsVerification existingRecord = existingRecordOptional.get();
                if (existingRecord.getValidUntil().isAfter(now) && existingRecord.getValidUntil().minusMinutes(1).isAfter(now)) {
                    // 验证码仍然有效且有效时间大于1分钟，不重新发送
                    return false;
                }
                existingRecord.setVerificationCode(code);
                existingRecord.setCreatedAt(now);
                existingRecord.setValidUntil(validUntil);
                existingRecord.setRequestCount(existingRecord.getRequestCount() + 1);
                smsVerificationRepository.save(existingRecord);
            } else {
                SmsVerification smsVerification = new SmsVerification();
                smsVerification.setPhoneNumber(phoneNumber);
                smsVerification.setVerificationCode(code);
                smsVerification.setCreatedAt(now);
                smsVerification.setValidUntil(validUntil);
                smsVerification.setRequestCount(1);
                smsVerificationRepository.save(smsVerification);
            }

            SendSmsRequest sendSmsRequest = new SendSmsRequest()
                    .setPhoneNumbers(phoneNumber)
                    .setSignName("时光漫游")
                    .setTemplateCode("SMS_470615097")
                    .setTemplateParam("{\"code\":\"" + code + "\"}");

            smsClient.sendSmsWithOptions(sendSmsRequest, new RuntimeOptions());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean verifyCode(String phoneNumber, String code) {
        Optional<SmsVerification> smsVerification = smsVerificationRepository.findByPhoneNumber(phoneNumber);
        return smsVerification.isPresent() && smsVerification.get().getVerificationCode().equals(code) && smsVerification.get().getValidUntil().isAfter(LocalDateTime.now());
    }
}
