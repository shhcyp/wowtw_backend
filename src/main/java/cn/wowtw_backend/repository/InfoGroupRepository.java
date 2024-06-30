package cn.wowtw_backend.repository;

import cn.wowtw_backend.model.infoGroup.GearExtra;
import cn.wowtw_backend.model.infoGroup.InfoGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InfoGroupRepository extends JpaRepository<InfoGroup, Integer> {

    @Query("SELECT ig FROM InfoGroup ig JOIN TalentInfoGroup tig ON ig.id = tig.infoGroup.id WHERE tig.talent.id = :talentId")
    List<InfoGroup> findInfoGroupByTalentId(Integer talentId);


}
