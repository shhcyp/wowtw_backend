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
@Table(name = "talents_clothes")
@EqualsAndHashCode(callSuper = true)
public class TalentsClothes extends TalentsGearBase {

    @ManyToOne
    @JoinColumn(name = "clothes_id")
    @JsonIgnore
    private Clothes clothes;
}