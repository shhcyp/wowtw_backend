package cn.wowtw_backend.model.user;

import jakarta.persistence.*;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "wowtw_users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;

    private Integer userID;

    private String password;

    private String phoneNumber;

    private String question;

    private String answer;

    private String avatar;

    private String nickname;

    private String identifier;

    private String inviteIdentifier;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    @Column(name = "last_avatar_update")
    private LocalDateTime lastAvatarUpdate;

    private String editCount;

    // 新增支付相关字段
}
