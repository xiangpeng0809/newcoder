package com.newcoder.community.config;


import com.newcoder.community.filter.LoginTicketFilter;
import com.newcoder.community.util.CommunityConstant;
import com.newcoder.community.util.CommunityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import java.io.PrintWriter;

/**
 * ClassName: SecurityConfig
 * Package: com.newcoder.community.config
 * Description:
 *
 * @author 向鹏
 * @version 1.0
 * @create 2025/5/4 18:03
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig implements CommunityConstant {

    @Autowired
    private LoginTicketFilter loginTicketFilter;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/resources/**");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((auth) -> auth
                .requestMatchers(
                        "/user/setting",
                        "/user/upload",
                        "/discuss/add",
                        "/comment/add/**",
                        "/letter/**",
                        "/notice/**",
                        "/like",
                        "/follow",
                        "/unfollow"
                ).hasAnyAuthority(
                        AUTHORITY_USER,
                        AUTHORITY_MODERATOR,
                        AUTHORITY_ADMIN
                    )
                .requestMatchers("/discuss/top","/discuss/good")
                    .hasAnyAuthority(
                        AUTHORITY_MODERATOR,
                        AUTHORITY_ADMIN
                    )
                .requestMatchers("/discuss/delete","/data/**").hasAuthority(
                        AUTHORITY_ADMIN
                    )
                .anyRequest().permitAll()
            ).csrf((csrf)-> csrf.disable())
        // 权限不够时处理
            .exceptionHandling((exception) -> exception
                .authenticationEntryPoint((request, response, authException) -> {
                    // 没有登录
                    String xRequestedWith = request.getHeader("x-requested-with");
                    if ("XMLHttpRequest".equals(xRequestedWith)) {
                        response.setContentType("application/plain;charset=utf-8");
                        PrintWriter writer = response.getWriter();
                        writer.write(CommunityUtil.getJSONString(403, "你还没有登录"));
                    } else {
                        response.sendRedirect(request.getContextPath() + "/login");
                    }
                })
                .accessDeniedHandler((request, response, accessDeniedException) -> {
                    // 权限不足
                    String xRequestedWith = request.getHeader("x-requested-with");
                    if ("XMLHttpRequest".equals(xRequestedWith)) {
                        response.setContentType("application/plain;charset=utf-8");
                        PrintWriter writer = response.getWriter();
                        writer.write(CommunityUtil.getJSONString(403, "你没有访问此功能的权限!"));
                    } else {
                        response.sendRedirect(request.getContextPath() + "/denied");
                    }
                })
        )
        // Security底层默认会拦截/logout请求，进行推出处理
        // 覆盖它默认的逻辑，才能执行我们自己的退出代码
            .logout((logout) -> logout.logoutUrl("/securityLogout"))
                .addFilterBefore(loginTicketFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
