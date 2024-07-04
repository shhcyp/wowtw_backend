package cn.wowtw_backend.repository;

import cn.wowtw_backend.model.infoGroup.GearBaseDTO;
import cn.wowtw_backend.model.infoGroup.Miscellanea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MiscellaneaRepository extends JpaRepository<Miscellanea, Integer> {

    @Query("SELECT new cn.wowtw_backend.model.infoGroup.GearBaseDTO(t.id, m.icon, m.part, m.name, m.quality, t.isMark, m.drop, t.isExtra) FROM Miscellanea m JOIN TalentsMiscellanea t ON m.id = t.miscellanea.id WHERE t.talents.id = :talentId")
    List<GearBaseDTO> findMiscellaneaByTalentId(Integer talentId);
}