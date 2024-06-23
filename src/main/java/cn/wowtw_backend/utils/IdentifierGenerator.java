package cn.wowtw_backend.utils;

import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Set;

public class IdentifierGenerator {
    private static final String UPPERCASE_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private static final String ALPHANUMERIC = UPPERCASE_LETTERS + DIGITS;
    private static final int SEGMENT_LENGTH = 4;
    private static final int SEGMENTS = 7;
    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generateUniqueIdentifier(Set<String> existingIdentifiers) {
        String identifier;
        do {
            identifier = generateIdentifier();
        } while (existingIdentifiers.contains(identifier));
        return identifier;
    }

    private static String generateIdentifier() {
        StringBuilder identifier = new StringBuilder();
        for (int i = 0; i < SEGMENTS; i++) {
            if (i > 0) {
                identifier.append('-');
            }
            identifier.append(generateSegment());
        }
        return identifier.toString();
    }

    private static String generateSegment() {
        StringBuilder segment = new StringBuilder();
        segment.append(UPPERCASE_LETTERS.charAt(RANDOM.nextInt(UPPERCASE_LETTERS.length())));
        segment.append(DIGITS.charAt(RANDOM.nextInt(DIGITS.length())));
        for (int i = 2; i < SEGMENT_LENGTH; i++) {
            segment.append(ALPHANUMERIC.charAt(RANDOM.nextInt(ALPHANUMERIC.length())));
        }
        return segment.toString();
    }

    public static void main(String[] args) {
        // 示例：如何生成唯一标识码
        Set<String> existingIdentifiers = new HashSet<>();
        // 示例：添加一些已经存在的标识码
        existingIdentifiers.add("A1B2-C3D4-E5F6-G7H8-I9J0-K1L2-M3N4");

        String newIdentifier = generateUniqueIdentifier(existingIdentifiers);
        System.out.println("Generated Identifier: " + newIdentifier);
    }
}
