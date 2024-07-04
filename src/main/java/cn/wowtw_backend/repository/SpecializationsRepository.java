package cn.wowtw_backend.repository;

import cn.wowtw_backend.model.infoGroup.SpecializationTreesDTO;
import cn.wowtw_backend.model.infoGroup.Specializations;
import cn.wowtw_backend.model.infoGroup.SpecializationsDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SpecializationsRepository extends JpaRepository<Specializations, Integer> {

    @Query("SELECT new cn.wowtw_backend.model.infoGroup.SpecializationsDTO(t.id, s.name) FROM Specializations s JOIN TalentsSpecializations t ON s.id = t.specializations.id WHERE t.talents.id = :talentId")
    List<SpecializationsDTO> findSpecializationsByTalentId(Integer talentId);

    @Query("SELECT new cn.wowtw_backend.model.infoGroup.SpecializationTreesDTO(s.name, s.image) FROM SpecializationTrees s JOIN TalentsSpecializationsSpecializationTrees t ON s.id = t.talentsSpecializations.id WHERE t.talentsSpecializations.id = :specsId")
    List<SpecializationTreesDTO> findSpecializationTreesBySpecializationsId(Integer specsId);
}