package com.repository.core.aspect;

import com.repository.core.constants.CookieConstant;
import com.repository.core.constants.RedisConstant;
import com.repository.core.enums.ResultEnum;
import com.repository.core.exception.AuthorizeException;
import com.repository.core.utils.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;


/**
 * 权限校验切面类
 * @author schuyler
 */
@Aspect
@Component
@Slf4j
public class AuthorizeAspect {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;



    @Pointcut("execution(public * com.repository.core.controller.*.*(..))" +
            "&& !execution(public  * com.repository.core.controller.UserController.*(..))")
    public void verify() {}

    @Before("verify()")
    public void doVerify() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //获取cookie
        Cookie cookie = CookieUtil.getCookie(request, CookieConstant.COOKIE_TOKEN);
        if (cookie == null) {
            log.warn("【登录校验】{}", ResultEnum.AUTHORIZE_COOKIE_NOT_EXIST.getMessage());
            throw new AuthorizeException(ResultEnum.AUTHORIZE_COOKIE_NOT_EXIST);
        }

        //从redis中获取
        String redisValue = stringRedisTemplate.opsForValue().get(String.format(RedisConstant.REDIS_TOKEN, cookie.getValue()));
        if (StringUtils.isEmpty(redisValue)) {
            log.warn("【登录校验】{}", ResultEnum.AUTHORIZE_REDIS_NOT_EXIST.getMessage());
            throw new AuthorizeException(ResultEnum.AUTHORIZE_REDIS_NOT_EXIST);
        }
    }
}
