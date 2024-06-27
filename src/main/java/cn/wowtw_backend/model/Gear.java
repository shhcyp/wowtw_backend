package cn.wowtw_backend.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "wow_gears")
public class Gear {
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

    @ManyToMany(mappedBy = "gears")
    private List<Talent> talents;

    @ManyToMany
    @JoinTable(
            name = "wow_gears_gear_mark",
            joinColumns = @JoinColumn(name = "gear_id"),
            inverseJoinColumns = @JoinColumn(name = "gear_mark_id")
    )
    private List<GearMark> gearMarks;

    @ManyToMany
    @JoinTable(
            name = "wow_gears_gear_extra",
            joinColumns = @JoinColumn(name = "gear_id"),
            inverseJoinColumns = @JoinColumn(name = "gear_extra_id")
    )
    private List<GearExtra> gearExtras;
}
