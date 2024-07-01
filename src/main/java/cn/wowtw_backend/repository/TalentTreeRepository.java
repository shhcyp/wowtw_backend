package cn.wowtw_backend.repository;

import cn.wowtw_backend.model.infoGroup.TalentTree;
import cn.wowtw_backend.model.infoGroup.TalentTreeImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TalentTreeRepository extends JpaRepository<TalentTree, Integer> {

    @Query("SELECT tt FROM TalentTree tt JOIN TalentInfoGroupTalentTree titt ON tt.id = titt.talentTree.id WHERE titt.talent.id = :talentId")
    List<TalentTree> findBurstTalentTreeByTalentId(Integer talentId);

    @Query("SELECT tti FROM TalentTreeImage tti JOIN TalentTreeTreeImage ttti ON tti.id = ttti.talentTreeImage.id WHERE ttti.talentTree.id = :talentTreeId")
    List<TalentTreeImage> findTreeImagesByTalentTreeId(Integer talentTreeId);
}