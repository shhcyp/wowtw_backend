package cn.wowtw_backend.model.infoGroup;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class GearExtrasDTO {

    private String icon;

    private String description;

    private String quality;

    // quality值到描述的映射
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

    // 构造函数
    public GearExtrasDTO(String icon, String description, Byte quality) {
        this.icon = icon;
        this.description = description;
        this.quality = mapQuality(quality);
    }

    // 质量映射方法
    private String mapQuality(Byte quality) {
        return qualityMap.getOrDefault(quality, "unknown");
    }
}