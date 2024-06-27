package cn.wowtw_backend.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "wow_talents")
public class Talent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @ManyToMany
    @JoinTable(
            name = "wow_talent_gear",
            joinColumns = @JoinColumn(name = "talent_id"),
            inverseJoinColumns = @JoinColumn(name = "gear_id")
    )
    private List<Gear> gears;
}