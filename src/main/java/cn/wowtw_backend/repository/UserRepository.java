package cn.wowtw_backend.repository;

import cn.wowtw_backend.model.user.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    // 邀请码
    boolean existsByIdentifier(String identifier);

    // 根据用户名查询用户
    User getByUsername(String username);

    // 更新session_active
    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.sessionActive = :status WHERE u.id = :id")
    void updateSessionActive(Integer id, Boolean status);
}