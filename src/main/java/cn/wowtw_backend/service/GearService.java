package cn.wowtw_backend.service;

import cn.wowtw_backend.model.GearDTO;

import java.util.List;

public interface GearService {
    // 根据talentId查询装备
    List<GearDTO> getGearsByTalentId(Integer talentId);
}
