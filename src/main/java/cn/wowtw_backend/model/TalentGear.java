package cn.wowtw_backend.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "wow_talent_gear")
public class TalentGear {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "talent_id")
    private Integer talentId;

    @Column(name = "gear_id")
    private Integer gearId;

    private Integer sort;
}
