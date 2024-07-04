package cn.wowtw_backend.model.infoGroup;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "talents_miscellanea_gear_marks")
@EqualsAndHashCode(callSuper = true)
public class TalentsMiscellaneaGearMarks extends TalentsGearBaseGearMarks {

    @ManyToOne
    @JoinColumn(name = "talents_miscellanea_id")
    @JsonIgnore
    private TalentsMiscellanea talentsMiscellanea;
}