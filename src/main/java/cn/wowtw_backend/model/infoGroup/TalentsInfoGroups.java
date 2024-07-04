package cn.wowtw_backend.model.infoGroup;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "talents_info_groups")
public class TalentsInfoGroups {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "talents_id")
    private Talents talents;

    @ManyToOne
    @JoinColumn(name = "info_groups_id")
    private InfoGroups infoGroups;

    private Integer sort;
}