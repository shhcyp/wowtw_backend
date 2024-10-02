package cn.wowtw_backend.repository;

import cn.wowtw_backend.model.infoGroup.GearBaseDTO;
import cn.wowtw_backend.model.infoGroup.Weapons;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WeaponsRepository extends JpaRepository<Weapons, Long> {

    @Query("SELECT new cn.wowtw_backend.model.infoGroup.GearBaseDTO(t.id, w.icon, w.part, w.name, w.quality, t.isMark, w.drop, t.isExtra) FROM Weapons w JOIN TalentsTwoHandWeapons t ON w.id = t.weapons.id WHERE t.talents.id = :talentId ORDER BY t.sort")
    List<GearBaseDTO> findTwoHandWeaponsByTalentId(Integer talentId);

    @Query("SELECT new cn.wowtw_backend.model.infoGroup.GearBaseDTO(t.id, w.icon, w.part, w.name, w.quality, t.isMark, w.drop, t.isExtra) FROM Weapons w JOIN TalentsMainHandWeapons t ON w.id = t.weapons.id WHERE t.talents.id = :talentId  ORDER BY t.sort")
    List<GearBaseDTO> findMainHandWeaponsByTalentId(Integer talentId);

    @Query("SELECT new cn.wowtw_backend.model.infoGroup.GearBaseDTO(t.id, w.icon, w.part, w.name, w.quality, t.isMark, w.drop, t.isExtra) FROM Weapons w JOIN TalentsOffHandWeapons t ON w.id = t.weapons.id WHERE t.talents.id = :talentId  ORDER BY t.sort")
    List<GearBaseDTO> findOffHandWeaponsByTalentId(Integer talentId);
}
