package cn.wowtw_backend.repository;

import cn.wowtw_backend.model.infoGroup.GearBaseDTO;
import cn.wowtw_backend.model.infoGroup.Clothes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClothesRepository extends JpaRepository<Clothes, Integer> {

    @Query("SELECT new cn.wowtw_backend.model.infoGroup.GearBaseDTO(t.id, c.icon, c.part, c.name, c.quality, t.isMark, c.drop, t.isExtra) FROM Clothes c JOIN TalentsClothes t ON c.id = t.clothes.id WHERE t.talents.id = :talentId")
    List<GearBaseDTO> findClothesByTalentId(Integer talentId);
}