package cn.wowtw_backend.model.infoGroup;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "talents_specializations_specialization_trees")
public class TalentsSpecializationsSpecializationTrees {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "talents_specializations_id")
    @JsonIgnore
    private TalentsSpecializations talentsSpecializations;

    @ManyToOne
    @JoinColumn(name = "specialization_trees_id")
    @JsonIgnore
    private SpecializationTrees specializationTrees;

    private Integer sort;
}