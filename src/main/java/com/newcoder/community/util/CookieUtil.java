package com.newcoder.community.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

/**
 * ClassName: CookieUtil
 * Package: com.newcoder.community.util
 * Description:
 *
 * @author 向鹏
 * @version 1.0
 * @create 2025/4/20 16:17
 */
public class CookieUtil {

    public static String getValue(HttpServletRequest request, String name) {
        if (request == null || name == null) {
            throw new IllegalArgumentException("参数为空！");
        }

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

}
