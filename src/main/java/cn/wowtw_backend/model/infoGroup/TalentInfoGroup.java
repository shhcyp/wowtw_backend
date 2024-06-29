package cn.wowtw_backend.model.infoGroup;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "wow_talents_info_group")
public class TalentInfoGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "talent_id")
    // @JsonIgnore
    private Talent talent;

    @ManyToOne
    @JoinColumn(name = "info_group_id")
    // @JsonIgnore
    private InfoGroup infoGroup;

    private Integer sort;
}
