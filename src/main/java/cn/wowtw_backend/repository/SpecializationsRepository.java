package cn.wowtw_backend.repository;

import cn.wowtw_backend.model.infoGroup.Specializations;
import cn.wowtw_backend.model.infoGroup.SpecializationsDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SpecializationsRepository extends JpaRepository<Specializations, Integer> {

    @Query("SELECT new cn.wowtw_backend.model.infoGroup.SpecializationsDTO(t.id, s.name, s.string) FROM Specializations s JOIN TalentsSpecializations t ON s.id = t.specializations.id WHERE t.talents.id = :talentId  ORDER BY t.sort")
    List<SpecializationsDTO> findSpecializationsByTalentId(Integer talentId);
}