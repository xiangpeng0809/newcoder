package com.newcoder.community.filter;

import com.newcoder.community.entity.LoginTicket;
import com.newcoder.community.entity.User;
import com.newcoder.community.service.UserService;
import com.newcoder.community.util.CookieUtil;
import com.newcoder.community.util.HostHolder;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Date;

/**
 * ClassName: LoginTicketFilter
 * Package: com.newcoder.community.filter
 * Description:
 *
 * @author 向鹏
 * @version 1.0
 * @create 2025/5/5 11:33
 */
@Component
public class LoginTicketFilter extends OncePerRequestFilter {

    @Autowired
    private UserService userService;

    @Autowired
    private HostHolder hostHolder;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try{
            String ticket = CookieUtil.getValue(request,"ticket");

            if (ticket != null) {
                LoginTicket loginTicket = userService.findLoginTicket(ticket);
                if (loginTicket != null && loginTicket.getStatus() == 0 && loginTicket.getExpired().after(new Date())) {
                    User user = userService.findUserById(loginTicket.getUserId());
                    if (user != null) {
                        hostHolder.setUser(user);
                        Authentication authentication = new UsernamePasswordAuthenticationToken(
                                user, user.getPassword(), userService.getAuthorities(user.getId()));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            }

            filterChain.doFilter(request, response);
        } finally {
            hostHolder.clear();
        }
    }
}
