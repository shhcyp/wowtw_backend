package cn.wowtw_backend.repository;

import cn.wowtw_backend.model.infoGroup.GearMarks;
import cn.wowtw_backend.model.infoGroup.GearMarksDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GearMarksRepository extends JpaRepository<GearMarks, Integer> {

    @Query("SELECT new cn.wowtw_backend.model.infoGroup.GearMarksDTO(g.name, g.icon) FROM GearMarks g JOIN TalentsPlatesGearMarks t ON g.id = t.gearMarks.id WHERE t.talentsPlates.id = :gearId ORDER BY t.sort DESC")
    List<GearMarksDTO> findPlatesMarksByGearId(Integer gearId);

    @Query("SELECT new cn.wowtw_backend.model.infoGroup.GearMarksDTO(g.name, g.icon) FROM GearMarks g JOIN TalentsMailsGearMarks t ON g.id = t.gearMarks.id WHERE t.talentsMails.id = :gearId ORDER BY t.sort DESC")
    List<GearMarksDTO> findMailsMarksByGearId(Integer gearId);

    @Query("SELECT new cn.wowtw_backend.model.infoGroup.GearMarksDTO(g.name, g.icon) FROM GearMarks g JOIN TalentsLeathersGearMarks t ON g.id = t.gearMarks.id WHERE t.talentsLeathers.id = :gearId ORDER BY t.sort DESC")
    List<GearMarksDTO> findLeathersMarksByGearId(Integer gearId);

    @Query("SELECT new cn.wowtw_backend.model.infoGroup.GearMarksDTO(g.name, g.icon) FROM GearMarks g JOIN TalentsClothesGearMarks t ON g.id = t.gearMarks.id WHERE t.talentsClothes.id = :gearId ORDER BY t.sort DESC")
    List<GearMarksDTO> findClothesMarksByGearId(Integer gearId);

    @Query("SELECT new cn.wowtw_backend.model.infoGroup.GearMarksDTO(g.name, g.icon) FROM GearMarks g JOIN TalentsMiscellaneaGearMarks t ON g.id = t.gearMarks.id WHERE t.talentsMiscellanea.id = :gearId ORDER BY t.sort DESC")
    List<GearMarksDTO> findMiscellaneaMarksByGearId(Integer gearId);

    @Query("SELECT new cn.wowtw_backend.model.infoGroup.GearMarksDTO(g.name, g.icon) FROM GearMarks g JOIN TalentsTwoHandWeaponsGearMarks t ON g.id = t.gearMarks.id WHERE t.talentsTwoHandWeapons.id = :gearId ORDER BY t.sort DESC")
    List<GearMarksDTO> findTwoHandWeaponsMarksByGearId(Integer gearId);

    @Query("SELECT new cn.wowtw_backend.model.infoGroup.GearMarksDTO(g.name, g.icon) FROM GearMarks g JOIN TalentsMainHandWeaponsGearMarks t ON g.id = t.gearMarks.id WHERE t.talentsMainHandWeapons.id = :gearId ORDER BY t.sort DESC")
    List<GearMarksDTO> findMainHandWeaponsMarksByGearId(Integer gearId);

    @Query("SELECT new cn.wowtw_backend.model.infoGroup.GearMarksDTO(g.name, g.icon) FROM GearMarks g JOIN TalentsOffHandWeaponsGearMarks t ON g.id = t.gearMarks.id WHERE t.talentsOffHandWeapons.id = :gearId ORDER BY t.sort DESC")
    List<GearMarksDTO> findOffHandWeaponsMarksByGearId(Integer gearId);
}