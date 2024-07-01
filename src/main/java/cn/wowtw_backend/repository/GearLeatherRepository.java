package cn.wowtw_backend.repository;

import cn.wowtw_backend.model.infoGroup.GearBase;
import cn.wowtw_backend.model.infoGroup.GearLeather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GearLeatherRepository extends JpaRepository<GearLeather, Integer> {

    @Query("SELECT gl FROM GearLeather gl JOIN TalentInfoGroupGearLeather tigl ON gl.id = tigl.gearLeather.id WHERE tigl.talent.id = :talentId")
    List<GearBase> findGearLeatherByTalentId(Integer talentId);
}