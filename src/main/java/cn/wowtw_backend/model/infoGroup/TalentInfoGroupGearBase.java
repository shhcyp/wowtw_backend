package cn.wowtw_backend.model.infoGroup;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class TalentInfoGroupGearBase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "talent_id")
    @JsonIgnore
    private Talent talent;

    @ManyToOne
    @JoinColumn(name = "info_group_id")
    @JsonIgnore
    private InfoGroup infoGroup;

    private Integer sort;
}
