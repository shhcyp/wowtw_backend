package cn.wowtw_backend.utils;

import java.util.HashMap;
import java.util.Map;

public class QualityMapper {

    private static final Map<Byte, String> qualityMap = new HashMap<>();

    static {
        qualityMap.put((byte) 0, "normal");
        qualityMap.put((byte) 1, "good");
        qualityMap.put((byte) 2, "excellent");
        qualityMap.put((byte) 3, "epic");
        qualityMap.put((byte) 4, "legendary");
        qualityMap.put((byte) 5, "eternal");
        qualityMap.put((byte) 6, "inherit");
        qualityMap.put((byte) 7, "any");
    }

    public static String mapQuality(Byte quality) {
        return qualityMap.getOrDefault(quality, "unknown");
    }
}
