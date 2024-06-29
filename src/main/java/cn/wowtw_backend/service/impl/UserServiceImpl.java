package cn.wowtw_backend.service.impl;

import cn.wowtw_backend.mapper.UserMapper;
import cn.wowtw_backend.service.UserService;
import cn.wowtw_backend.utils.IdentifierGenerator;
import cn.wowtw_backend.model.user.User;
import cn.wowtw_backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    private int currentLength = 4;  // 初始长度为4位

    @Autowired
    public UserServiceImpl(UserMapper userMapper, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    // 查询所有用户信息
    @Override
    public List<User> list() {
        return userMapper.list();
    }

    // 用户名占用验证
    @Override
    public Boolean verifyUsername(String username) {
        return userMapper.countByUsername(username) == 0;
    }

    // 手机号是否未使用过验证
    @Override
    public Boolean verifyPhoneNumber(String phoneNumber) {
        return userMapper.countByPhoneNumber(phoneNumber) == 0;
    }

    // 用户注册
    @Override
    public Boolean register(User user) {
        // 生成userID随机数
        int randomNumber = generateUniqueRandomNumber();

        // 生成邀请码
        Set<String> existingIdentifiers = new HashSet<>(userRepository.findAll().stream().map(User::getIdentifier).toList());
        String uniqueIdentifier = IdentifierGenerator.generateUniqueIdentifier(existingIdentifiers);

        user.setUserID(randomNumber);
        user.setIdentifier(uniqueIdentifier);
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());

        // 密码加密
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        int result = userMapper.register(user);
        return result > 0;
    }

    // 邀请码生成
    private int generateUniqueRandomNumber() {
        Random random = new Random();
        int randomNumber = 0;
        boolean isUnique = false;
        while (!isUnique) {
            int min = (int) Math.pow(10, currentLength - 1);
            int max = (int) Math.pow(10, currentLength) - 1;
            randomNumber = random.nextInt(max - min + 1) + min;

            Integer existingNumber = userMapper.findUserID(randomNumber);
            if (existingNumber == null) {
                isUnique = true;
            } else if (isRangeExhausted(min, max)) {
                currentLength++;
            }
        }
        return randomNumber;
    }

    private boolean isRangeExhausted(int min, int max) {
        for (int i = min; i <= max; i++) {
            if (userMapper.findUserID(i) == null) {
                return false;
            }
        }
        return true;
    }

    // 用户登录
    // 获取数据库中用户数据
    @Override
    public User login(String username) {
        return userMapper.getByUsername(username);
    }

    // 密码验证
    @Override
    public Boolean checkPassword(User user, String rawPassword) {
        return passwordEncoder.matches(rawPassword, user.getPassword());
    }

    // 找回密码验证用户名手机号
    @Override
    public User usernamePhoneNumberVerify(User user) {
        return userMapper.usernamePhoneVerify(user);
    }

    // 找回密码验证密保答案
    @Override
    public Boolean answerMatchVerify(User user) {
        return userMapper.countByAnswer(user) == 1;
    }

    // 重置密码
    @Override
    public Boolean resetPassword(User user) {
        // 密码加密
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userMapper.resetPassword(user);
    }

    // 修改头像，一周一次的，先废弃
//    @Override
    // @Transactional
    public void updateAvatar(Integer id, String newAvatarUrl) throws Exception {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new Exception("用户不存在");
        }

        User user = userOptional.get();
        LocalDateTime now = LocalDateTime.now();

        if (user.getLastAvatarUpdate() != null && ChronoUnit.DAYS.between(user.getLastAvatarUpdate(), now) < 7) {
            throw new Exception("头像每周只能修改一次");
        }

        user.setAvatar(newAvatarUrl);
        user.setLastAvatarUpdate(now);
        userRepository.save(user);
    }

    // 修改头像
    @Override
    public int updateAvatarFree(User user) {
        return userMapper.updateAvatar(user);
    }

    // 修改昵称
    @Override
    public Boolean updateNickname(User renickname) {
        return userMapper.updateNickname(renickname);
    }

    // 邀请码
    @Override
    @Transactional
    public String generateAndSaveUniqueIdentifier(Integer id) {
        Set<String> existingIdentifiers = new HashSet<>(userRepository.findAll().stream().map(User::getIdentifier).toList());
        String uniqueIdentifier = IdentifierGenerator.generateUniqueIdentifier(existingIdentifiers);

        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user.setIdentifier(uniqueIdentifier);
        userRepository.save(user);

        return uniqueIdentifier;
    }

    @Override
    public boolean checkIdentifierExists(String identifier) {
        return userRepository.existsByIdentifier(identifier);
    }

}
