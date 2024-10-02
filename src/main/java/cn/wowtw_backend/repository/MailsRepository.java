package cn.wowtw_backend.repository;

import cn.wowtw_backend.model.infoGroup.GearBaseDTO;
import cn.wowtw_backend.model.infoGroup.Mails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MailsRepository extends JpaRepository<Mails, Integer> {

    @Query("SELECT new cn.wowtw_backend.model.infoGroup.GearBaseDTO(t.id, m.icon, m.part, m.name, m.quality, t.isMark, m.drop, t.isExtra) FROM Mails m JOIN TalentsMails t ON m.id = t.mails.id WHERE t.talents.id = :talentId  ORDER BY t.sort")
    List<GearBaseDTO> findMailsByTalentId(Integer talentId);
}