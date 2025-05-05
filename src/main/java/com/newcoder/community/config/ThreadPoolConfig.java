package com.newcoder.community.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
/**
 * ClassName: ThreadPoolConfig
 * Package: com.newcoder.community.config
 * Description:
 *
 * @author 向鹏
 * @version 1.0
 * @create 2025/5/5 17:09
 */
@Configuration
@EnableScheduling
@EnableAsync
public class ThreadPoolConfig {
}
