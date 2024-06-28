package cn.wowtw_backend.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "wow_plates")
public class Plate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String icon;
    private String part;
    private String name;
    private Byte quality;
    private Byte isMark;
    private String drop;
    private Byte isExtra;

    @ManyToMany(mappedBy = "plates")
    private List<Talent> talents;

    @ManyToMany(mappedBy = "plates")
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
