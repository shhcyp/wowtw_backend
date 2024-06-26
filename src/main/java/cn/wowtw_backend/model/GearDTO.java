package cn.wowtw_backend.model;

import lombok.Data;

import java.util.List;

@Data
public class GearDTO {
    private String icon;
    private String part;
    private String name;
    private Integer quality;
    private Integer isMark;
    private List<GearMarkDTO> mark;
    private String drop;
    private Integer isExtra;
    private List<GearExtraDTO> gearExtra;
}
