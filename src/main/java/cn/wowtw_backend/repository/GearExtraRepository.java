package cn.wowtw_backend.repository;

import cn.wowtw_backend.model.GearExtra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GearExtraRepository extends JpaRepository<GearExtra, Integer> {
    List<GearExtra> findByGearId(Integer gearId);
}
