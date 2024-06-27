package cn.wowtw_backend.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "wow_gears_gear_mark")
public class GearsGearMark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "gear_id")
    private Integer gearId;

    @Column(name = "gear_mark_id")
    private Byte gearMarkId;

    private Byte sort;
}
