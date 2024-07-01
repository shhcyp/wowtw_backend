package cn.wowtw_backend.model.infoGroup;

import lombok.Data;

import java.util.List;

@Data
public class InfoGroupDetailTalentTreeDTO implements InfoGroupDetail {

    private String talentName;

    private List<TalentTreeImageDTO> images;
}