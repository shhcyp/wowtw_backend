package cn.wowtw_backend.repository;

import cn.wowtw_backend.model.infoGroup.GearBase;
import cn.wowtw_backend.model.infoGroup.GearCloth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GearClothRepository extends JpaRepository<GearCloth, Integer> {

    @Query("SELECT gc FROM GearCloth gc JOIN TalentInfoGroupGearCloth tigc ON gc.id = tigc.gearCloth.id WHERE tigc.talent.id = :talentId")
    List<GearBase> findGearClothByTalentId(Integer talentId);
}