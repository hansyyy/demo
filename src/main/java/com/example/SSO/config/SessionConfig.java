package com.example.SSO.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @Author HanSiyue
 * @Date 2019/12/8 下午3:03
 */
@EnableRedisHttpSession
@Configuration
public class SessionConfig {
}
