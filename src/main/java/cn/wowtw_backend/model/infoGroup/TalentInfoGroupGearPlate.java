package cn.wowtw_backend.model.infoGroup;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "wow_talents_info_group_plates")
public class TalentInfoGroupGearPlate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "talent_id")
    private Talent talent;

    @ManyToOne
    @JoinColumn(name = "info_group_id")
    private InfoGroup infoGroup;

    @ManyToOne
    @JoinColumn(name = "plate_id")
    private GearPlate gearPlate;

    private Integer sort;
}
