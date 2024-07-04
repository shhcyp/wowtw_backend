package cn.wowtw_backend.model.infoGroup;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "talents_plates_gear_marks")
@EqualsAndHashCode(callSuper = true)
public class TalentsPlatesGearMarks extends TalentsGearBaseGearMarks {

    @ManyToOne
    @JoinColumn(name = "talents_plates_id")
    @JsonIgnore
    private TalentsPlates talentsPlates;
}