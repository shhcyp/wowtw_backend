package cn.wowtw_backend.repository;

import cn.wowtw_backend.model.Gear;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GearRepository extends JpaRepository<Gear, Integer> {
    List<Gear> findByTalentId(Integer talentId);
}
