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

    @Column(name = "userID")
    private Integer userID;

    private String password;

    @Column(name = "phone_number")
    private String phoneNumber;

    private String question;

    private String answer;

    private String avatar;

    private String nickname;

    private String identifier;

    @Column(name = "invite_identifier")
    private String inviteIdentifier;

    @Column(name = "create_time")
    private LocalDateTime createTime;

    @Column(name = "update_time")
    private LocalDateTime updateTime;

    @Column(name = "payment_info")
    private String paymentInfo;

    @Column(name = "session_id")
    private String sessionId;

    @Column(name = "session_active")
    private Boolean sessionActive;
}
