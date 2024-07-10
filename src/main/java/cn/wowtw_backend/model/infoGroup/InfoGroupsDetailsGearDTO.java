package cn.wowtw_backend.model.infoGroup;

import cn.wowtw_backend.utils.QualityMapper;
import lombok.Data;

import java.util.List;

@Data
public class InfoGroupsDetailsGearDTO implements InfoGroupsDetails {

    private String icon;

    private String part;

    private String name;

    private String quality;

    private Boolean isMark;

    private List<GearMarksDTO> marks;

    private String drop;

    private Boolean isExtra;

    private List<GearExtrasDTO> extras;

    public void setQuality(Byte quality) {
        this.quality = QualityMapper.mapQuality(quality);
    }
}