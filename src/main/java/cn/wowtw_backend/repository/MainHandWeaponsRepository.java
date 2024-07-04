package cn.wowtw_backend.repository;

import cn.wowtw_backend.model.infoGroup.GearBaseDTO;
import cn.wowtw_backend.model.infoGroup.MainHandWeapons;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MainHandWeaponsRepository extends JpaRepository<MainHandWeapons, Integer> {

    @Query("SELECT new cn.wowtw_backend.model.infoGroup.GearBaseDTO(t.id, m.icon, m.part, m.name, m.quality, t.isMark, m.drop, t.isExtra) FROM MainHandWeapons m JOIN TalentsMainHandWeapons t ON m.id = t.mainHandWeapons.id WHERE t.talents.id = :talentId")
    List<GearBaseDTO> findMainHandWeaponsByTalentId(Integer talentId);
}
