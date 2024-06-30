package cn.wowtw_backend.service;

import cn.wowtw_backend.model.infoGroup.GearPlate;
import cn.wowtw_backend.model.infoGroup.GearPlateDTO;

import java.util.List;

public interface TestService {

    List<GearPlateDTO> findThingByTalentId(Integer talentId);
}
