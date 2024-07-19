package cn.wowtw_backend.utils;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Base64;

public class KeyGeneratorUtil {
    public static void main(String[] args) throws Exception {
        // 使用 HMAC-SHA256 算法生成密钥
        KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
        keyGenerator.init(256); // 密钥长度为256位
        SecretKey secretKey = keyGenerator.generateKey();
        
        // 将密钥编码为 Base64 字符串
        String base64Key = Base64.getEncoder().encodeToString(secretKey.getEncoded());
        System.out.println("Base64 Encoded Secret Key: " + base64Key);

        //gtORUQljR76IC6MzysLBR8hZOZMCqswGF0jymCRkixE=
    }
}