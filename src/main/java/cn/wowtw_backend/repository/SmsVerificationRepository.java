package cn.wowtw_backend.repository;

import cn.wowtw_backend.model.sms.SmsVerification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SmsVerificationRepository extends JpaRepository<SmsVerification, Long> {
    Optional<SmsVerification> findByPhoneNumber(String phoneNumber);
}