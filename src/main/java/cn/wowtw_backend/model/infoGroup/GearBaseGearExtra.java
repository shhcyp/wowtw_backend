package cn.wowtw_backend.model.infoGroup;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class GearBaseGearExtra {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "talent_id")
    @JsonIgnore
    private Talent talent;

    @ManyToOne
    @JoinColumn(name = "gear_extra_id")
    @JsonIgnore
    private GearExtra gearExtra;

    private Integer sort;
}