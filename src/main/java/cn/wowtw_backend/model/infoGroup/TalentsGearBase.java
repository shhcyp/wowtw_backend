package cn.wowtw_backend.model.infoGroup;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class TalentsGearBase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "talents_id")
    @JsonIgnore
    private Talents talents;

    private Boolean isMark;

    private Boolean isExtra;

    private Double sort;
}