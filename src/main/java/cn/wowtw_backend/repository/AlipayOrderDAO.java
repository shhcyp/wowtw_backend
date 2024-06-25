package cn.wowtw_backend.repository;

import cn.wowtw_backend.model.AlipayOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlipayOrderDAO extends JpaRepository<AlipayOrder, Long> {
}
