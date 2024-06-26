package cn.wowtw_backend.mapper;

import cn.wowtw_backend.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface UserMapper {
    // 查询所有用户数据
    @Select("select * from wowtw_users")
    List<User> list();

    // 用户名占用验证，返回匹配数
    @Select("select count(*) from wowtw_users where username = #{username}")
    int countByUsername(String username);

    // 查询所有手机号字段，返回匹配数量
    @Select("select count(*) from wowtw_users where phone_number = #{phoneNumber}")
    int countByPhoneNumber(String phoneNumber);

    // 用户注册，返回插入行数，为1则注册成功，否则失败
    @Insert("insert into wowtw_users(username, userID, password, phone_number, question, answer, identifier, invite_identifier, create_time, update_time) values (#{username}, #{userID}, #{password}, #{phoneNumber}, #{question}, #{answer}, #{identifier}, #{inviteIdentifier}, #{createTime}, #{updateTime})")
    int register(User user);

    // 为userID准备的一个接口
    @Select("SELECT userID FROM wowtw_users WHERE userID = #{userID}")
    Integer findUserID(Integer userID);

    // 用户登录
    @Select("select * from wowtw_users where username = #{username}")
    User getByUsername(String username);

    // 找回密码用户名手机号验证
    @Select("select * from wowtw_users where username = #{username} and phone_number = #{phoneNumber}")
    User usernamePhoneVerify(User user);

    // 找回密码密保问题验证
    @Select("select count(*) from wowtw_users where username = #{username} and answer = #{answer}")
    int countByAnswer(User user);

    // 重置密码
    @Update("update wowtw_users set password = #{password} where username = #{username}")
    Boolean resetPassword(User user);

    @Update("update wowtw_users set avatar = #{avatar} where id = #{id}")
    int updateAvatar(User user);

    @Update("update wowtw_users set nickname = #{nickname} where id = #{id}")
    Boolean updateNickname(User renickname);
}
