package cn.wowtw_backend.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class OrderUtil {
    public static String generateOrderNo() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSS");
        String dateStr = sdf.format(new Date());
        String randomStr = getRandomNumericString();
        return dateStr + randomStr;
    }

    private static String getRandomNumericString() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(7);
        for (int i = 0; i < 7; i++) {
            sb.append(random.nextInt(10));  // 生成0-9之间的随机数
        }
        return sb.toString();
    }

    public static void printGeneratedOrderNo() {
        String orderNo = generateOrderNo();
        System.out.println("Generated Order Number: " + orderNo);
    }

    public static void main(String[] args) {
        printGeneratedOrderNo();
    }
}