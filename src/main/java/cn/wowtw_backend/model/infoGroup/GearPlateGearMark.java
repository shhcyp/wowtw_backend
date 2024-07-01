package cn.wowtw_backend.model.infoGroup;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "wow_plates_gear_marks")
@EqualsAndHashCode(callSuper = true)
public class GearPlateGearMark extends GearBaseGearMark {

    @ManyToOne
    @JoinColumn(name = "plate_id")
    @JsonIgnore
    private GearPlate gearPlate;
}