package cn.wowtw_backend.repository;

import cn.wowtw_backend.model.infoGroup.GearMark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GearMarkRepository extends JpaRepository<GearMark, Integer> {

    @Query("SELECT gm FROM GearMark gm JOIN GearPlateGearMark gpgm ON gm.id = gpgm.gearMark.id WHERE gpgm.gearPlate.id = :gearId")
    List<GearMark> findGearMarksByGearId(Integer gearId);
}