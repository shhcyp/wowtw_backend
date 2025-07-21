package cn.wowtw_backend.config;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.teaopenapi.models.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AliyunSmsConfig {

    private static final Logger log = LoggerFactory.getLogger(AliyunSmsConfig.class);
    @Value("${ALIBABA_CLOUD_ACCESS_KEY_ID}")
    private String accessKeyId;

    @Value("${ALIBABA_CLOUD_ACCESS_KEY_SECRET}")
    private String accessKeySecret;

    @Bean
    public Client smsClient() throws Exception {
        Config config = new Config()
                .setAccessKeyId(accessKeyId)
                .setAccessKeySecret(accessKeySecret);
        config.endpoint = "dysmsapi.aliyuncs.com";
        log.info(accessKeyId);
        log.info(accessKeySecret);
        return new Client(config);
    }
}
