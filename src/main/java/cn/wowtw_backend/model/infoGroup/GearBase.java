package cn.wowtw_backend.model.infoGroup;

import jakarta.persistence.*;
import lombok.Data;

@Data
@MappedSuperclass
public class GearBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String icon;

    private String part;

    private String name;

    private Byte quality;

    @Column(name = "is_mark")
    private Boolean isMark;

    private String drop;

    @Column(name = "is_extra")
    private Boolean isExtra;
}