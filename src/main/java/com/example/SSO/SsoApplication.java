package com.example.SSO;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@MapperScan("com.example.SSO.dao")
@SpringBootApplication
public class SsoApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(SsoApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder applicationBuilder){
        return applicationBuilder.sources(SsoApplication.class);
    }

}
