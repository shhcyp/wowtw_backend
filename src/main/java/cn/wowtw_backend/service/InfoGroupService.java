package cn.wowtw_backend.service;

import cn.wowtw_backend.model.infoGroup.InfoGroupResponseDTO;

import java.util.List;

public interface InfoGroupService {
    List<InfoGroupResponseDTO> getInfoGroupByTalentId(Integer talentId);
}
