package cn.wowtw_backend.model.infoGroup;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "wow_plates_gear_marks")
public class GearPlateGearMark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "plate_id")
    @JsonIgnore
    private GearPlate gearPlate;

    @ManyToOne
    @JoinColumn(name = "gear_mark_id")
    private GearMark gearMark;

    private Integer sort;
}
