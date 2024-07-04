package cn.wowtw_backend.model.infoGroup;

import lombok.AllArgsConstructor;
import lombok.Data;

// 用于阻止JpaRepository自动去重
@Data
@AllArgsConstructor
public class GearBaseDTO {

    private Integer id;

    private String icon;

    private String part;

    private String name;

    private Byte quality;

    private Boolean isMark;

    private String drop;

    private Boolean isExtra;
}
