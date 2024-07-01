package cn.wowtw_backend.repository;

import cn.wowtw_backend.model.infoGroup.GearBase;
import cn.wowtw_backend.model.infoGroup.GearMiscellaneous;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GearMiscellaneousRepository extends JpaRepository<GearMiscellaneous, Integer> {

    @Query("SELECT gm FROM GearMiscellaneous gm JOIN TalentInfoGroupGearMiscellaneous tigm ON gm.id = tigm.gearMiscellaneous.id WHERE tigm.talent.id = :talentId")
    List<GearBase> findGearMiscellaneousByTalentId(Integer talentId);
}