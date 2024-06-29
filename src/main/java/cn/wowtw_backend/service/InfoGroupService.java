package cn.wowtw_backend.service;

import cn.wowtw_backend.model.infoGroup.InfoGroup;

import java.util.List;

public interface InfoGroupService {
    List<InfoGroup> getInfoGroupByTalentId(Integer talentId);
}
