package cn.wowtw_backend.repository;

import cn.wowtw_backend.model.infoGroup.GearBase;
import cn.wowtw_backend.model.infoGroup.GearMail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GearMailRepository extends JpaRepository<GearMail, Integer> {

    @Query("SELECT gm FROM GearMail gm JOIN TalentInfoGroupGearMail tigm ON gm.id = tigm.gearMail.id WHERE tigm.talent.id = :talentId")
    List<GearBase> findGearMailByTalentId(Integer talentId);
}