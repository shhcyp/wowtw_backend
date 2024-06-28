package cn.wowtw_backend.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "wow_info_group")
public class InfoGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Byte id;
    private String name;

    @ManyToMany(mappedBy = "infoGroups")
    private List<Talent> talents;

    @ManyToMany
    @JoinTable(
            name = "wow_talents_info_group_plates",
            joinColumns = @JoinColumn(name = "info_group_id"),
            inverseJoinColumns = @JoinColumn(name = "plate_id")
    )
    private List<Plate> plates;
}
