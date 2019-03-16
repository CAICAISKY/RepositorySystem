package com.repository.core.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Cookie工具类
 * @author schuyler
 */
public class CookieUtil {

    /**
     * 设置cookie
     * @param response 响应对象
     * @param name cookie名
     * @param value cookie值
     * @param expire cookie有效时间
     */
    public static void setCookie(HttpServletResponse response, String name, String value, Integer expire) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge(expire);
        response.addCookie(cookie);
    }

    /**
     * 设置cookie-浏览器关闭即失效
     * @param response 响应对象
     * @param name cookie名字
     * @param value cookie值
     */
    public static void setCookie(HttpServletResponse response, String name, String value) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    /**
     * 获取对应cookie
     * @param request
     * @param cookieName
     * @return
     */
    public static Cookie getCookie(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        Map<String, Cookie> map = new HashMap<>();
        if (cookies != null) {
            map = arr2Map(cookies);
        }
        if (map.containsKey(cookieName)) {
            return map.get(cookieName);
        }
        return null;
    }

    /**
     * cookie数组转map
     * @param cookies
     * @return
     */
    private static Map<String, Cookie> arr2Map(Cookie[] cookies) {
        Map<String, Cookie> map = new HashMap<>();
        for (Cookie cookie : cookies) {
            map.put(cookie.getName(), cookie);
        }
        return map;
    }
}
