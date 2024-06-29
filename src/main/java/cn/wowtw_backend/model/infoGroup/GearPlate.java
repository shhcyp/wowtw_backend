package cn.wowtw_backend.model.infoGroup;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
//@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "wow_plates")
public class GearPlate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String icon;
    private String part;
    private String name;
    private Byte quality;
    private Boolean isMark;
    private String drop;
    private Boolean isExtra;

    @ManyToMany
    @JoinTable(
            name = "wow_talents_info_group_plates",
            joinColumns = @JoinColumn(name = "plate_id"),
            inverseJoinColumns = @JoinColumn(name = "talent_id")
    )
    private List<Talent> talents;

    @ManyToMany
    @JoinTable(
            name = "wow_talents_info_group_plates",
            joinColumns = @JoinColumn(name = "plate_id"),
            inverseJoinColumns = @JoinColumn(name = "info_group_id")
    )
    private List<InfoGroup> infoGroups;

    @ManyToMany
    @JoinTable(
            name = "wow_plates_gear_marks",
            joinColumns = @JoinColumn(name = "plate_id"),
            inverseJoinColumns = @JoinColumn(name = "gear_mark_id")
    )
    private List<GearMark> gearMarks;

    @ManyToMany
    @JoinTable(
            name = "wow_plates_gear_extras",
            joinColumns = @JoinColumn(name = "plate_id"),
            inverseJoinColumns = @JoinColumn(name = "gear_extra_id")
    )
    private List<GearExtra> gearExtras;
}
