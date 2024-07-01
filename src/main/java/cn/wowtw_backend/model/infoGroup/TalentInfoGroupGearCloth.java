package cn.wowtw_backend.model.infoGroup;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "wow_talents_info_group_clothes")
@EqualsAndHashCode(callSuper = true)
public class TalentInfoGroupGearCloth extends TalentInfoGroupGearBase{

    @ManyToOne
    @JoinColumn(name = "cloth_id")
    @JsonIgnore
    private GearCloth gearCloth;
}