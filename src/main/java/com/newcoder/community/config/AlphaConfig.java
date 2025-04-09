package com.newcoder.community.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;

/**
 * ClassName: AlphaConfig
 * Package: com.newcoder.community.config
 * Description:
 *
 * @author 向鹏
 * @version 1.0
 * @create 2025/4/1 18:37
 */
@Configuration
public class AlphaConfig {

    @Bean
    public SimpleDateFormat simpleDateFormat() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
}
