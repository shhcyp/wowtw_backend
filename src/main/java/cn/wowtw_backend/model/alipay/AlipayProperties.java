package cn.wowtw_backend.model.alipay;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "alipay")
public class AlipayProperties {
    // private String privateKey;
    private String alipayPublicKey;
    private String serverUrl;
    private String appId;
    private String format;
    private String charset;
    private String signType;
    private String notifyUrl;
    private String encryptKey;
}
