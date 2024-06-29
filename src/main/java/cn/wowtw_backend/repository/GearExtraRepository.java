package cn.wowtw_backend.repository;

import cn.wowtw_backend.model.infoGroup.GearExtra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GearExtraRepository extends JpaRepository<GearExtra, Integer> {
}
