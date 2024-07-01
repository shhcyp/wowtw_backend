package cn.wowtw_backend.model.infoGroup;

import lombok.Data;

import java.util.List;

@Data
public class TalentTreeDTO {

    private String title;

    private List<TalentTreeImageDTO> talentTreeImages;
}
