package cn.wowtw_backend.repository;

import cn.wowtw_backend.model.alipay.AlipayOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlipayOrderRepository extends JpaRepository<AlipayOrder, Long> {
}