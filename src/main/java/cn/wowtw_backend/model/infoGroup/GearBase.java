package cn.wowtw_backend.model.infoGroup;

import jakarta.persistence.*;
import lombok.Data;

//@Data
//@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class GearBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String icon;
    private String part;
    private String name;
    private Byte quality;
    private Boolean isMark;
    private String drop;
    private Boolean isExtra;
}
