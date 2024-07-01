package cn.wowtw_backend.model.infoGroup;

import lombok.Data;

import java.util.List;

@Data
public class InfoGroupDetailGearDTO implements InfoGroupDetail{

    private String icon;

    private String part;

    private String name;

    private Byte quality;

    private Boolean isMark;

    private List<GearMarkDTO> marks;

    private String drop;

    private Boolean isExtra;

    private List<GearExtraDTO> extras;
}
