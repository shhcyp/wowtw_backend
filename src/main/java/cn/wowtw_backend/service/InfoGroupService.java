package cn.wowtw_backend.service;

import cn.wowtw_backend.model.infoGroup.InfoGroupsResponseDTO;

import java.util.List;

public interface InfoGroupService {
    List<InfoGroupsResponseDTO> getInfoGroupByTalentId(Integer talentId);
}