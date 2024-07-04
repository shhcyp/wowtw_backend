package cn.wowtw_backend.repository;

import cn.wowtw_backend.model.infoGroup.GearBaseDTO;
import cn.wowtw_backend.model.infoGroup.OffHandWeapons;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OffHandWeaponsRepository extends JpaRepository<OffHandWeapons, Integer> {

    @Query("SELECT new cn.wowtw_backend.model.infoGroup.GearBaseDTO(t.id, o.icon, o.part, o.name, o.quality, t.isMark, o.drop, t.isExtra) FROM OffHandWeapons o JOIN TalentsOffHandWeapons t ON o.id = t.offHandWeapons.id WHERE t.talents.id = :talentId")
    List<GearBaseDTO> findOffHandWeaponsByTalentId(Integer talentId);
}
