package cn.wowtw_backend.repository;

import cn.wowtw_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    // 邀请码
    boolean existsByIdentifier(String identifier);
}
