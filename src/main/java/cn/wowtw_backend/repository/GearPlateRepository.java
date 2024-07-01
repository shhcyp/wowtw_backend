package cn.wowtw_backend.repository;

import cn.wowtw_backend.model.infoGroup.GearBase;
import cn.wowtw_backend.model.infoGroup.GearPlate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GearPlateRepository extends JpaRepository<GearPlate, Integer> {

    @Query("SELECT gp FROM GearPlate gp JOIN TalentInfoGroupGearPlate tigp ON gp.id = tigp.gearPlate.id WHERE tigp.talent.id = :talentId")
    List<GearBase> findGearPlateByTalentId(Integer talentId);
}