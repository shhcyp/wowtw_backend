package cn.wowtw_backend.model;

import lombok.Data;

import java.util.List;

@Data
public class GearDTO {
    private String icon;
    private String part;
    private String name;
    private Byte quality;
    private Byte isMark;
    private List<GearMarkDTO> marks;
    private String drop;
    private Byte isExtra;
    private List<GearExtraDTO> gearExtra;
}
