package cn.wowtw_backend.model.infoGroup;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "wow_talents_info_group_plates")
@EqualsAndHashCode(callSuper = true)
public class TalentInfoGroupGearPlate extends TalentInfoGroupGearBase {

    @ManyToOne
    @JoinColumn(name = "plate_id")
    @JsonIgnore
    private GearPlate gearPlate;
}