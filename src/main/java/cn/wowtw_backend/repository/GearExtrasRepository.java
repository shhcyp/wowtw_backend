package cn.wowtw_backend.repository;

import cn.wowtw_backend.model.infoGroup.GearExtras;
import cn.wowtw_backend.model.infoGroup.GearExtrasDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GearExtrasRepository extends JpaRepository<GearExtras, Integer> {

    @Query("SELECT new cn.wowtw_backend.model.infoGroup.GearExtrasDTO(g.icon, g.description, g.quality) FROM GearExtras g JOIN TalentsPlatesGearExtras t ON g.id = t.gearExtras.id WHERE t.talentsPlates.id = :gearId")
    List<GearExtrasDTO> findPlatesExtrasByGearId(Integer gearId);

    @Query("SELECT new cn.wowtw_backend.model.infoGroup.GearExtrasDTO(g.icon, g.description, g.quality) FROM GearExtras g JOIN TalentsMailsGearExtras t ON g.id = t.gearExtras.id WHERE t.talentsMails.id = :gearId")
    List<GearExtrasDTO> findMailsExtrasByGearId(Integer gearId);

    @Query("SELECT new cn.wowtw_backend.model.infoGroup.GearExtrasDTO(g.icon, g.description, g.quality) FROM GearExtras g JOIN TalentsLeathersGearExtras t ON g.id = t.gearExtras.id WHERE t.talentsLeathers.id = :gearId")
    List<GearExtrasDTO> findLeathersExtrasByGearId(Integer gearId);

    @Query("SELECT new cn.wowtw_backend.model.infoGroup.GearExtrasDTO(g.icon, g.description, g.quality) FROM GearExtras g JOIN TalentsClothesGearExtras t ON g.id = t.gearExtras.id WHERE t.talentsClothes.id = :gearId")
    List<GearExtrasDTO> findClothesExtrasByGearId(Integer gearId);

    @Query("SELECT new cn.wowtw_backend.model.infoGroup.GearExtrasDTO(g.icon, g.description, g.quality) FROM GearExtras g JOIN TalentsMiscellaneaGearExtras t ON g.id = t.gearExtras.id WHERE t.talentsMiscellanea.id = :gearId")
    List<GearExtrasDTO> findMiscellaneaExtrasByGearId(Integer gearId);

    @Query("SELECT new cn.wowtw_backend.model.infoGroup.GearExtrasDTO(g.icon, g.description, g.quality) FROM GearExtras g JOIN TalentsTwoHandWeaponsGearExtras t ON g.id = t.gearExtras.id WHERE t.talentsTwoHandWeapons.id = :gearId")
    List<GearExtrasDTO> findTwoHandWeaponsExtrasByGearId(Integer gearId);

    @Query("SELECT new cn.wowtw_backend.model.infoGroup.GearExtrasDTO(g.icon, g.description, g.quality) FROM GearExtras g JOIN TalentsMainHandWeaponsGearExtras t ON g.id = t.gearExtras.id WHERE t.talentsMainHandWeapons.id = :gearId")
    List<GearExtrasDTO> findMainHandWeaponsExtrasByGearId(Integer gearId);

    @Query("SELECT new cn.wowtw_backend.model.infoGroup.GearExtrasDTO(g.icon, g.description, g.quality) FROM GearExtras g JOIN TalentsOffHandWeaponsGearExtras t ON g.id = t.gearExtras.id WHERE t.talentsOffHandWeapons.id = :gearId")
    List<GearExtrasDTO> findOffHandWeaponsExtrasByGearId(Integer gearId);
}