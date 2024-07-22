package cn.wowtw_backend.service;

import cn.wowtw_backend.model.user.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    // 查询所有用户
    List<User> list();

    // 注册
    Boolean register(User user);

    // 用户名校验
    Boolean verifyUsername(String username);

    // 手机号校验
    Boolean verifyPhoneNumber(String phoneNumber);

    // 登录
    User login(String username);

    // 登录响应数据
    Map<String, Object> generateLoginResponse(User userInDB);

    // 登录限制用
    User getByUsername(String username);

    // 更新会话标识
    void updateSessionId(Integer userId, String sessionId);

    // 密码校验
    Boolean checkPassword(User user, String rawPassword);

    // 用户名手机号匹配校验，用于重置密码
    User usernamePhoneNumberVerify(User user);

    // 密保答案校验
    Boolean answerMatchVerify(User user);

    // 修改密码
    Boolean resetPassword(User user);

    // 修改头像
    int updateAvatarFree(User user);

    // 修改昵称
    Boolean updateNickname(User renickname);

    // 邀请码生成
    String generateAndSaveUniqueIdentifier(Integer id);

    // 邀请码校验
    boolean checkIdentifierExists(String identifier);
}