package cn.wowtw_backend.model.infoGroup;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "talents_plates")
@EqualsAndHashCode(callSuper = true)
public class TalentsPlates extends TalentsGearBase {

    @ManyToOne
    @JoinColumn(name = "plates_id")
    @JsonIgnore
    private Plates plates;
}