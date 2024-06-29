package cn.wowtw_backend.model.infoGroup;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "wow_gear_extra")
public class GearExtra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String icon;
    private String description;
    private Byte quality;
}
