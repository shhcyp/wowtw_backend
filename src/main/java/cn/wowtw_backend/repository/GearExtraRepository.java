package cn.wowtw_backend.repository;

import cn.wowtw_backend.model.infoGroup.GearExtra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GearExtraRepository extends JpaRepository<GearExtra, Integer> {

    @Query("SELECT ge FROM GearExtra ge JOIN GearPlateGearExtra gpge ON ge.id = gpge.gearExtra.id WHERE gpge.talent.id = :talentId")
    List<GearExtra> findGearExtrasByTalentId(Integer talentId);
}
