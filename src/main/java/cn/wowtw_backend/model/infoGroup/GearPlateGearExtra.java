package cn.wowtw_backend.model.infoGroup;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "wow_plates_gear_extras")
public class GearPlateGearExtra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "talent_id")
    private Talent talent;

    @ManyToOne
    @JoinColumn(name = "plate_id")
    private GearPlate gearPlate;

    @ManyToOne
    @JoinColumn(name = "gear_extra_id")
    private GearExtra gearExtra;

    private Integer sort;
}
