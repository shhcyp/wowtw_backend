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
@Table(name = "talents_leathers")
@EqualsAndHashCode(callSuper = true)
public class TalentsLeathers extends TalentsGearBase {

    @ManyToOne
    @JoinColumn(name = "leathers_id")
    @JsonIgnore
    private Leathers leathers;
}