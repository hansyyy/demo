package com.example.SSO.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @Author HanSiyue
 * @Date 2019/9/18 下午3:24
 */
@EnableRedisHttpSession
@Configuration
public class SessionConfig {
}