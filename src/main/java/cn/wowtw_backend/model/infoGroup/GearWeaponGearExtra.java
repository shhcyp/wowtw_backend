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
@Table(name = "wow_weapons_gear_extras")
@EqualsAndHashCode(callSuper = true)
public class GearWeaponGearExtra extends GearBaseGearExtra {

    @ManyToOne
    @JoinColumn(name = "weapon_id")
    @JsonIgnore
    private GearWeapon gearWeapon;
}
