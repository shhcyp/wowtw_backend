package cn.wowtw_backend.repository;

import cn.wowtw_backend.model.infoGroup.GearBase;
import cn.wowtw_backend.model.infoGroup.GearExtra;
import cn.wowtw_backend.model.infoGroup.GearMark;
import cn.wowtw_backend.model.infoGroup.GearPlate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GearPlateRepository extends JpaRepository<GearPlate, Integer> {
    @Query("SELECT gp FROM GearPlate gp JOIN TalentInfoGroupGearPlate tggp ON gp.id = tggp.gearPlate.id WHERE tggp.talent.id = :talentId")
    List<GearBase> findGearPlateByTalentId(Integer talentId);




}
