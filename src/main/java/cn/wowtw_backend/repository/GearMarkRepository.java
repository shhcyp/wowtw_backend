package cn.wowtw_backend.repository;

import cn.wowtw_backend.model.GearMark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GearMarkRepository extends JpaRepository<GearMark, Integer> {
    List<GearMark> findByGearId(Integer gearId);
}
