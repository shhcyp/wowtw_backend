package cn.wowtw_backend.config;

import cn.wowtw_backend.model.alipay.AlipayProperties;
import com.alipay.api.AlipayConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Configuration
public class MyAlipayConfig {
    private final AlipayProperties alipayProperties;
    public MyAlipayConfig(AlipayProperties alipayProperties) {
        this.alipayProperties = alipayProperties;
    }

    AlipayConfig alipayConfig = new AlipayConfig();
    @Bean
    public AlipayConfig initAlipayConfig() {
        alipayConfig.setServerUrl(alipayProperties.getServerUrl());
        alipayConfig.setAppId(alipayProperties.getAppId());
        //  alipayConfig.setPrivateKey(alipayProperties.getPrivateKey());

       try {
            // String alipayPrivateKey = System.getenv("ALIPAY_PRIVATE_KEY");
            alipayConfig.setPrivateKey(new String(Files.readAllBytes(Paths.get("/etc/AlipayPrivateKey/alipay_private_key.txt"))));
            /*
            if (alipayPrivateKey != null) {
                alipayConfig.setPrivateKey(alipayPrivateKey);
            } else {
                System.err.println("alipayPrivateKey not set");
            }
            */
        } catch (Exception e) {
            e.printStackTrace();
        }

        alipayConfig.setFormat(alipayProperties.getFormat());
        alipayConfig.setAlipayPublicKey(alipayProperties.getAlipayPublicKey());
        alipayConfig.setCharset(alipayProperties.getCharset());
        alipayConfig.setSignType(alipayProperties.getSignType());

        return alipayConfig;
    }
}
