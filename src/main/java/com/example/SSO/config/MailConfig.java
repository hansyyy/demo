package com.example.SSO.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * @Author HanSiyue
 * @Date 2019/10/14 下午4:39
 */
@Configuration
public class MailConfig {
    @Bean
    public JavaMailSenderImpl JavaMailSender(){
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.qq.com");
        mailSender.setUsername("975444913@qq.com");
        mailSender.setPassword("callmehansiyue11");
        return  mailSender;
    }
}



