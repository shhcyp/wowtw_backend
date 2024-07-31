package cn.wowtw_backend.controller;

import cn.wowtw_backend.service.VisitCountService;
import cn.wowtw_backend.utils.Result;
import cn.wowtw_backend.service.UserService;
import cn.wowtw_backend.model.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;
    private final VisitCountService visitCountService;

    public UserController(UserService userService, VisitCountService visitCountService) {
        this.userService = userService;
        this.visitCountService = visitCountService;
    }

    // Hello
    @GetMapping("/hello")
    public Result hello() {
        log.info("===有访客===");
        return Result.success("Hello! I'm here!");
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
        log.info("===查询手机号{}是否可用===", phoneNumber);
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
        log.info("==={}用户注册===", user.getUsername());
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
        log.info("===用户{}登录===", user.getUsername());
        User userInDB = userService.login(user.getUsername());
        if (userInDB == null) {
            return Result.fail("账号或密码错误");
        }

        Boolean isMatch = userService.checkPassword(userInDB, user.getPassword());
        if (isMatch) {
            Map<String, Object> data = userService.generateLoginResponse(userInDB);
            log.info("===用户{}登录成功===", userInDB.getUsername());
            return new Result(1, "登录成功，跳转中", data);
        }
        return Result.fail("账号或密码错误");
    }

    // 找回密码用户名、手机号验证
    @PostMapping("/user/resetpassword/verify")
    public Result usernamePhoneNumberVerify(@RequestBody User user) {
        // log.info("===找回密码第一步验证{}===", user.getUsername());
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
        // log.info("===密保问题验证{}===", user.getUsername());
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
        // log.info("==={}用户重置密码===", user.getUsername());
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
        // log.info("===用户{}修改头像{}===", user.getId(), user.getAvatar());
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
        // log.info("===用户{}修改昵称{}===", renickname.getId(), renickname.getNickname());
        Boolean result = userService.updateNickname(renickname);
        return result ? Result.success(renickname.getNickname()) : Result.fail();
    }

    // 邀请码验证
    @GetMapping("/identifiers/exists")
    public Result inviteIDExists(String identifier) {
        // log.info("===查询{}是否存在===", identifier);
        boolean result = userService.checkIdentifierExists(identifier);
        return result ? Result.success("邀请码可用") : Result.fail("无效邀请码");
    }

    // 装备数量统计
    @GetMapping("/user/gearCount")
    public Result gearCount() {
        Long gearCount = userService.getGearCount();
        return Result.success(gearCount);
    }

    // 访问量
    @GetMapping("/visitCount")
    public Result getVisitCount() {
        int presentCount = visitCountService.getVisitCount();
        return Result.success(presentCount);
    }
}
