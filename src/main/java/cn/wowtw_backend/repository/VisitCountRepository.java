package cn.wowtw_backend.repository;

import cn.wowtw_backend.model.user.VisitCount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitCountRepository extends JpaRepository<VisitCount, Long> {
}
