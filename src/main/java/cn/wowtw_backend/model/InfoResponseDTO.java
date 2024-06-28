package cn.wowtw_backend.model;

import lombok.Data;

import java.util.List;

@Data
public class InfoResponseDTO {
    private List<GearGroupDTO> gearGroup;
    private List<TalentTreeDTO> talentTrees;
}
