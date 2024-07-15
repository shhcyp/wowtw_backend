package cn.wowtw_backend.repository;

import cn.wowtw_backend.model.infoGroup.GearBaseDTO;
import cn.wowtw_backend.model.infoGroup.Leathers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LeathersRepository extends JpaRepository<Leathers, Integer> {

    @Query("SELECT new cn.wowtw_backend.model.infoGroup.GearBaseDTO(t.id, l.icon, l.part, l.name, l.quality, t.isMark, l.drop, t.isExtra) FROM Leathers l JOIN TalentsLeathers t ON l.id = t.leathers.id WHERE t.talents.id = :talentId ORDER BY t.sort")
    List<GearBaseDTO> findLeathersByTalentId(Integer talentId);
}