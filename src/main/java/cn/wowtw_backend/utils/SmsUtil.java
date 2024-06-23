package cn.wowtw_backend.utils;

import java.security.SecureRandom;

public class SmsUtil {
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final int CODE_LENGTH = 6;

    public static String generateCode() {
        StringBuilder code = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            code.append(RANDOM.nextInt(10));
        }
        return code.toString();
    }
}
