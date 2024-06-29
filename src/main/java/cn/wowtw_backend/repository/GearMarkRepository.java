package cn.wowtw_backend.repository;

import cn.wowtw_backend.model.infoGroup.GearMark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GearMarkRepository extends JpaRepository<GearMark, Integer> {
}
