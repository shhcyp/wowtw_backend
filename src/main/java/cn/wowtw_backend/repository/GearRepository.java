package cn.wowtw_backend.repository;

import cn.wowtw_backend.model.Gear;
import cn.wowtw_backend.model.GearExtra;
import cn.wowtw_backend.model.GearMark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GearRepository extends JpaRepository<Gear, Integer> {

    @Query("SELECT g FROM Gear g JOIN TalentGear tg ON g.id = tg.gearId WHERE tg.talentId = :talentId ORDER BY tg.sort ASC")
    List<Gear> findGearsByTalentId(Integer talentId);

    @Query("SELECT gm FROM GearMark gm JOIN GearsGearMark ggm ON gm.id = ggm.gearMarkId WHERE ggm.gearId = :gearId ORDER BY ggm.sort")
    List<GearMark> findGearMarksByGearId(Integer gearId);

    @Query("SELECT ge FROM GearExtra ge JOIN GearsGearExtra gge ON ge.id = gge.gearExtraId WHERE gge.gearId = :gearId ORDER BY gge.sort")
    List<GearExtra> findGearExtrasByGearId(Integer gearId);
}
