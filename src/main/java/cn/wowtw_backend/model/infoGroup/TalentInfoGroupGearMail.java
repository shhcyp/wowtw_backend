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
@Table(name = "wow_talents_info_group_mails")
@EqualsAndHashCode(callSuper = true)
public class TalentInfoGroupGearMail extends TalentInfoGroupGearBase {

    @ManyToOne
    @JoinColumn(name = "mail_id")
    @JsonIgnore
    private GearMail gearMail;
}
