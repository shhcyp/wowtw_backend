package cn.wowtw_backend.repository;

import cn.wowtw_backend.model.infoGroup.Talent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TalentRepository extends JpaRepository<Talent, Integer> {
}
