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
@Table(name = "talents_main_hand_weapons_gear_marks")
@EqualsAndHashCode(callSuper = true)
public class TalentsMainHandWeaponsGearMarks extends TalentsGearBaseGearMarks {

    @ManyToOne
    @JoinColumn(name = "talents_main_hand_weapons_id")
    @JsonIgnore
    private TalentsMainHandWeapons talentsMainHandWeapons;
}
