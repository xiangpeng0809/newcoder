package com.newcoder.community.config;


/**
 * ClassName: SecurityConfig
 * Package: com.newcoder.community.config
 * Description:
 *
 * @author 向鹏
 * @version 1.0
 * @create 2025/5/4 18:03
 */
/*
@Configuration
@EnableWebSecurity
public class SecurityConfig implements CommunityConstant {

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
                        AUTHORITY_ADMIN,
                        AUTHORITY_MODERATOR,
                        AUTHORITY_USER
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
            .logout((logout) -> logout.logoutUrl("/securityLogout"));

        return http.build();
    }
}*/
