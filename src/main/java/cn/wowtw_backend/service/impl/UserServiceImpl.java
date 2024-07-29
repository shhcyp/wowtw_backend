package cn.wowtw_backend.service.impl;

import cn.wowtw_backend.mapper.UserMapper;
import cn.wowtw_backend.repository.*;
import cn.wowtw_backend.service.UserService;
import cn.wowtw_backend.utils.CustomWebSocketHandler;
import cn.wowtw_backend.utils.IdentifierGenerator;
import cn.wowtw_backend.model.user.User;
import cn.wowtw_backend.utils.JwtUtils;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    private final CustomWebSocketHandler webSocketHandler;

    private final UserRepository userRepository;
    private final TalentsClothesRepository talentsClothesRepository;
    private final TalentsLeathersRepository talentsLeathersRepository;
    private final TalentsMailsRepository talentsMailsRepository;
    private final TalentsMainHandWeaponsRepository talentsMainHandWeaponsRepository;
    private final TalentsMiscellaneaRepository talentsMiscellaneaRepository;
    private final TalentsOffHandWeaponsRepository talentsOffHandWeaponsRepository;
    private final TalentsPlatesRepository talentsPlatesRepository;
    private final TalentsTwoHandWeaponsRepository talentsTwoHandWeaponsRepository;

    private int currentLength = 4;  // 初始长度为4位

    @Autowired
    public UserServiceImpl(UserMapper userMapper, PasswordEncoder passwordEncoder, UserRepository userRepository, CustomWebSocketHandler webSocketHandler, TalentsClothesRepository talentsClothesRepository, TalentsLeathersRepository talentsLeathersRepository, TalentsMailsRepository talentsMailsRepository, TalentsMainHandWeaponsRepository talentsMainHandWeaponsRepository, TalentsMiscellaneaRepository talentsMiscellaneaRepository, TalentsOffHandWeaponsRepository talentsOffHandWeaponsRepository, TalentsPlatesRepository talentsPlatesRepository, TalentsTwoHandWeaponsRepository talentsTwoHandWeaponsRepository) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.webSocketHandler = webSocketHandler;
        this.talentsClothesRepository = talentsClothesRepository;
        this.talentsLeathersRepository = talentsLeathersRepository;
        this.talentsMailsRepository = talentsMailsRepository;
        this.talentsMainHandWeaponsRepository = talentsMainHandWeaponsRepository;
        this.talentsMiscellaneaRepository = talentsMiscellaneaRepository;
        this.talentsOffHandWeaponsRepository = talentsOffHandWeaponsRepository;
        this.talentsPlatesRepository = talentsPlatesRepository;
        this.talentsTwoHandWeaponsRepository = talentsTwoHandWeaponsRepository;
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
        user.setSessionId("init");

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
        return userRepository.getByUsername(username);
    }

    // 更新会话标识
    public void updateSessionId(Integer userId, String sessionId) {
        log.debug("更新用户 {} 的会话ID: {}", userId, sessionId);
        userMapper.updateSessionId(userId, sessionId);
    }

    @Override
    public Map<String, Object> generateLoginResponse(User userInDB) {
        // 使之前的会话无效
        userRepository.updateSessionActive(userInDB.getId(), false);

        // 通知会话无效
        webSocketHandler.notifySessionInvalid(userInDB.getUsername());

        // 生成会话标识
        String sessionId = UUID.randomUUID().toString();
        userInDB.setSessionId(sessionId);
        // 更新数据库中的用户记录
        userRepository.save(userInDB);

        Map<String, Object> claims = new HashMap<>();
        claims.put("username", userInDB.getUsername());
        claims.put("sessionId", sessionId);

        String token = JwtUtils.generateJwt(userInDB.getUsername(), claims);
        log.debug("生成的JWT: {}", token);

        Map<String, Object> data = new HashMap<>();
        data.put("id", userInDB.getId());
        data.put("username", userInDB.getUsername());
        data.put("userID", userInDB.getUserID());
        data.put("userAvatar", userInDB.getAvatar());
        data.put("nickname", userInDB.getNickname());
        data.put("identifier", userInDB.getIdentifier());
        data.put("token", token);
        data.put("sessionId", sessionId);

        // 激活新的会话
        userRepository.updateSessionActive(userInDB.getId(), true);

        return data;
    }

    // 登录限制专用
    @Override
    public User getByUsername(String username) {
        log.debug("根据用户名 {} 获取用户信息", username);
        return userRepository.getByUsername(username);
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

    @Override
    public Long getGearCount() {
        Long clothes = talentsClothesRepository.count();
        Long leathers = talentsLeathersRepository.count();
        Long mails = talentsMailsRepository.count();
        Long mainHandWps = talentsMainHandWeaponsRepository.count();
        Long miscellanea = talentsMiscellaneaRepository.count();
        Long offHandWps = talentsOffHandWeaponsRepository.count();
        Long Plates = talentsPlatesRepository.count();
        Long twoHandWps = talentsTwoHandWeaponsRepository.count();

        return clothes + leathers + mails + mainHandWps + miscellanea + offHandWps + Plates + twoHandWps;
    }
}
