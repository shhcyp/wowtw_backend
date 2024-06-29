package cn.wowtw_backend.model.infoGroup;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "wow_gears_gear_extra")
public class GearsGearExtra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "gear_id")
    private Integer gearId;

    @Column(name = "gear_extra_id")
    private Short gearExtraId;

    private Byte sort;
}
