package cn.wowtw_backend.repository;

import cn.wowtw_backend.model.infoGroup.GearBaseDTO;
import cn.wowtw_backend.model.infoGroup.Plates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PlatesRepository extends JpaRepository<Plates, Integer> {

    @Query("SELECT new cn.wowtw_backend.model.infoGroup.GearBaseDTO(t.id, p.icon, p.part, p.name, p.quality, t.isMark, p.drop, t.isExtra) FROM Plates p JOIN TalentsPlates t ON p.id = t.plates.id WHERE t.talents.id = :talentId ORDER BY t.sort")
    List<GearBaseDTO> findPlatesByTalentId(Integer talentId);
}