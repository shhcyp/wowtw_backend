package cn.wowtw_backend.model.infoGroup;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "wow_plates_gear_extras")
@EqualsAndHashCode(callSuper = true)
public class GearPlateGearExtra extends GearBaseGearExtra {

    @ManyToOne
    @JoinColumn(name = "plate_id")
    @JsonIgnore
    private GearPlate gearPlate;
}