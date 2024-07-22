package cn.wowtw_backend.service;

import cn.wowtw_backend.model.infoGroup.InfoGroupsResponseDTO;
import cn.wowtw_backend.model.infoGroup.Talents;

import java.util.List;

public interface InfoGroupService {
    // 获取信息组
    List<InfoGroupsResponseDTO> getInfoGroupByTalentId(Integer talentId);

    // 获取最新装备版本
    List<Talents> getLatestTalentsVersion();
}