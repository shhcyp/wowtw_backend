package cn.wowtw_backend.repository;

import cn.wowtw_backend.model.infoGroup.InfoGroups;
import cn.wowtw_backend.model.infoGroup.InfoGroupsDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InfoGroupsRepository extends JpaRepository<InfoGroups, Integer> {

    @Query("SELECT new cn.wowtw_backend.model.infoGroup.InfoGroupsDTO(i.id, i.name) FROM InfoGroups i JOIN TalentsInfoGroups t ON i.id = t.infoGroups.id WHERE t.talents.id = :talentId")
    List<InfoGroupsDTO> findInfoGroupsByTalentId(Integer talentId);
}