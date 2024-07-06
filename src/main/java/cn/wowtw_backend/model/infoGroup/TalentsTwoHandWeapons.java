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
@Table(name = "talents_two_hand_weapons")
@EqualsAndHashCode(callSuper = true)
public class TalentsTwoHandWeapons extends TalentsGearBase {

    @ManyToOne
    @JoinColumn(name = "weapons_id")
    @JsonIgnore
    private Weapons weapons;
}