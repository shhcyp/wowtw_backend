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
@Table(name = "wow_talents_info_group_talent_trees")
@EqualsAndHashCode(callSuper = true)
public class TalentInfoGroupTalentTree extends TalentInfoGroupGearBase {

    @ManyToOne
    @JoinColumn(name = "talent_tree_id")
    @JsonIgnore
    private TalentTree talentTree;
}