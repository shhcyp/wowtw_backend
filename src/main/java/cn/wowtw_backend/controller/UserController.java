package cn.wowtw_backend.controller;

import cn.wowtw_backend.utils.Result;
import cn.wowtw_backend.service.UserService;
import cn.wowtw_backend.utils.JwtUtils;
import cn.wowtw_backend.model.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Hello
    @GetMapping("/user/hello")
    public String hello() {
        return "I'm here!";
    }

    // 查询所有用户数据
    @GetMapping("/user/users")
    public Result list() {
        log.info("查询用户数据");
        List<User> userList = userService.list();
        return Result.success(userList);
    }

    // 用户名占用验证
    @GetMapping("/user/usernameverify")
    public Result verifyUsername(String username) {
        log.info("查询用户名{}是否被占用", username);
        Boolean isUsable = userService.verifyUsername(username);
        if (isUsable) {
            return new Result(1, "用户名可用", username);
        } else {
            return new Result(0, "用户名不可用", username);
        }
    }

    // 手机号占用验证
    @GetMapping("/user/phonenumberverify")
    public Result verifyPhoneNumber(String phoneNumber) {
        log.info("查询手机号{}是否可用", phoneNumber);
        Boolean isUsable = userService.verifyPhoneNumber(phoneNumber);
        if (isUsable) {
            return new Result(1, "手机号可用", phoneNumber);
        } else {
            return new Result(0, "手机号已经注册过", phoneNumber);
        }
    }

    // 用户注册
    @PostMapping("/user/register")
    public Result register(@RequestBody User user) {
        log.info("{}用户注册", user);
        boolean result = userService.register(user);
        if (result) {
            return new Result(1, "注册成功，跳转中", null);
        } else {
            return new Result(0, "注册失败，请刷新页面重试", null);
        }
    }

    // 用户登录
    @PostMapping("/user/login")
    public Result login(@RequestBody User user) {
        log.info("用户登录{}", user);
        User userInDB = userService.login(user.getUsername());
        if (userInDB == null) {
            return Result.fail("账号或密码错误");
        }

        Boolean isMatch = userService.checkPassword(userInDB, user.getPassword());
        if (isMatch) {
            Map<String, Object> claims = new HashMap<>();
            claims.put("username", userInDB.getUsername());

            String token = JwtUtils.generateJwt(claims);

            Map<String, Object> data = new HashMap<>();
            data.put("id", userInDB.getId());
            data.put("userID", userInDB.getUserID());
            data.put("userAvatar", userInDB.getAvatar());
            data.put("nickname", userInDB.getNickname());
            data.put("identifier", userInDB.getIdentifier());
            data.put("editCount", userInDB.getEditCount());
            data.put("token", token);

            log.info("token为：{}", token);

            return new Result(1, "登录成功，跳转中", data);
        }
        return Result.fail("账号或密码错误");
    }

    // 找回密码用户名、手机号验证
    @PostMapping("/user/resetpassword/verify")
    public Result usernamePhoneNumberVerify(@RequestBody User user) {
        log.info("找回密码第一步验证{}", user);
        User u = userService.usernamePhoneNumberVerify(user);
        if (u != null) {
            return new Result(1, "验证通过，请回答密保问题", u.getQuestion());
        } else {
            return Result.fail("账号和手机号不匹配");
        }
    }

    // 找回密码问题答案验证
    @PostMapping("/user/resetpassword/match")
    public Result answerMatchVerify(@RequestBody User user) {
        log.info("密保问题验证{}", user);
        Boolean isMatch = userService.answerMatchVerify(user);
        if (isMatch) {
            return Result.success("验证通过，请输入新密码");
        } else {
            return Result.fail("答案错误");
        }
    }

    // 重置密码
    @PostMapping("/user/resetpassword/submit")
    public Result resetPassword(@RequestBody User user) {
        log.info("{}用户重置密码", user.getUsername());
        Boolean result = userService.resetPassword(user);
        if (result) {
            return Result.success("重置成功，跳转中");
        } else {
            return Result.fail("重置失败");
        }
    }

    // 修改头像
    @PostMapping("/user/avatar")
    public Result updateAvatarFree(@RequestBody User user) {
        log.info("用户{}修改头像{}", user.getId(), user.getAvatar());
        int result = userService.updateAvatarFree(user);
        if (result == 1) {
            return Result.success("修改成功");
        } else {
            return Result.fail("修改失败");
        }
    }

    // 修改昵称
    @PostMapping("/user/nickname")
    public Result updateNickname(@RequestBody User renickname) {
        log.info("用户{}修改昵称{}", renickname.getId(), renickname.getNickname());
        Boolean result = userService.updateNickname(renickname);
        return result ? Result.success() : Result.fail();
    }
}
