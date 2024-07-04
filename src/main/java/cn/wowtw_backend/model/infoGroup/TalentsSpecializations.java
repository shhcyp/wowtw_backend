package cn.wowtw_backend.model.infoGroup;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "talents_specializations")
public class TalentsSpecializations {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "talents_id")
    @JsonIgnore
    private Talents talents;

    @ManyToOne
    @JoinColumn(name = "specializations_id")
    @JsonIgnore
    private Specializations specializations;

    private Integer sort;
}