package com.repository.core.service.impl;

import com.repository.core.constants.RedisConstant;
import com.repository.core.enity.UserInfo;
import com.repository.core.enums.ResultEnum;
import com.repository.core.exception.UserException;
import com.repository.core.repository.UserInfoRepository;
import com.repository.core.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;
import java.util.concurrent.TimeUnit;


/**
 * 用户Service实现类
 * @author schuyler
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private UserInfoRepository userInfoRepository;


    /***
     * 用户登录
     * @param userName
     * @param password
     * @return
     */
    @Override
    public UserInfo login(String userName, String password) {
        //1.通过用户名称获取用户信息然后匹配密码
        UserInfo userInfo = userInfoRepository.findByUserName(userName);
        if (userInfo == null) {
            log.info("【用户登录】{}", ResultEnum.USER_LOGIN_ERROR.getMessage());
            throw new UserException(ResultEnum.USER_LOGIN_ERROR);
        }
        if (! password.equals(userInfo.getPassword())) {
            log.info("【用户登录】{}", ResultEnum.USER_LOGIN_ERROR.getMessage());
            throw new UserException(ResultEnum.USER_LOGIN_ERROR);
        }

        return userInfo;
    }

    @Override
    public void logout() {

    }
}
