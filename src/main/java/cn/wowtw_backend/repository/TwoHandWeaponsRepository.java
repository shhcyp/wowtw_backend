package cn.wowtw_backend.repository;

import cn.wowtw_backend.model.infoGroup.GearBaseDTO;
import cn.wowtw_backend.model.infoGroup.TwoHandWeapons;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TwoHandWeaponsRepository extends JpaRepository<TwoHandWeapons, Integer> {

    @Query("SELECT new cn.wowtw_backend.model.infoGroup.GearBaseDTO(tt.id, t.icon, t.part, t.name, t.quality, tt.isMark, t.drop, tt.isExtra) FROM TwoHandWeapons t JOIN TalentsTwoHandWeapons tt ON t.id = tt.twoHandWeapons.id WHERE tt.talents.id = :talentId")
    List<GearBaseDTO> findTwoHandWeaponsByTalentId(Integer talentId);
}