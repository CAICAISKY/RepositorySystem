package com.repository.core.controller;

import com.repository.core.config.ProjectConfig;
import com.repository.core.constants.CookieConstant;
import com.repository.core.constants.RedisConstant;
import com.repository.core.constants.URLConstant;
import com.repository.core.enity.UserInfo;
import com.repository.core.enums.ResultEnum;
import com.repository.core.exception.UserException;
import com.repository.core.form.UserForm;
import com.repository.core.service.UserService;
import com.repository.core.utils.CookieUtil;
import com.repository.core.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * User控制层
 * @author schuyler
 */
@Slf4j
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ProjectConfig projectConfig;
    /**
     * 登录页面跳转
     * @return
     */
    @GetMapping("/loginPage")
    public ModelAndView loginPage() {
        return new ModelAndView(URLConstant.USER_LOGIN_PAGE_MODEL);
    }


    /**
     * 用户登录
     * @param userForm
     * @param bindingResult
     * @param response
     * @param map
     * @return
     */
    @PostMapping("/login")
    public ModelAndView login(@Valid UserForm userForm,
                              BindingResult bindingResult,
                              HttpServletResponse response,
                              HttpServletRequest request,
                              Map<String, Object> map) {

        if (bindingResult.hasErrors()) {
            log.error("【登录校验】出现异常,{}", bindingResult.getFieldError().getDefaultMessage());
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            map.put("url", URLConstant.USER_LOGIN_PAGE_URL);
            return new ModelAndView(URLConstant.ERROR_URL_MODEL, map);
        }

        UserInfo userInfo = new UserInfo();
        //1.进行登录
        try {
            userInfo = userService.login(userForm.getUserName(), userForm.getPassword());
        } catch (UserException e) {
            map.put("msg", e.getMessage());
            map.put("url", URLConstant.USER_LOGIN_PAGE_URL);
            return new ModelAndView(URLConstant.ERROR_URL_MODEL, map);
        }
        //2.将用户角色放入redis中留待权限校验

        String cookieValue = UUID.randomUUID().toString();
        stringRedisTemplate.opsForValue().set(String.format(RedisConstant.REDIS_TOKEN, cookieValue),
                userInfo.getUserRole(), RedisConstant.REDIS_EXPIRE, TimeUnit.SECONDS);

        //3.将token放入cookie中
        CookieUtil.setCookie(response, CookieConstant.COOKIE_TOKEN, cookieValue);

        //4.将用户信息放入Session中
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userInfo, userVO);
        HttpSession session = request.getSession();
        session.setAttribute("userVO", userVO);

        return new ModelAndView("redirect:".concat(URLConstant.PROVIDER_LIST_URL));
    }

    /**
     * 用户退出登录
     * @param request
     * @param response
     * @param map
     * @return
     */
    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request,
                               HttpServletResponse response,
                               Map<String, Object> map) {

        //1.去除redis中的相关信息
        Cookie cookie = CookieUtil.getCookie(request, CookieConstant.COOKIE_TOKEN);
        String redisKey = String.format(RedisConstant.REDIS_TOKEN, cookie.getValue());
        stringRedisTemplate.opsForValue().getOperations().delete(redisKey);

        //2.去除cookie
        CookieUtil.setCookie(response, CookieConstant.COOKIE_TOKEN, null, 0);

        //3.去除session中的相关信息
        request.getSession().removeAttribute("userVO");

        map.put("msg", ResultEnum.USER_LOGOUT_SUCCESS.getMessage());
        map.put("url", URLConstant.USER_LOGIN_PAGE_URL);
        return new ModelAndView(URLConstant.SUCCESS_URL_MODEL, map);
    }
}
