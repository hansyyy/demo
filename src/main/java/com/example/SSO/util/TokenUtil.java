package com.example.SSO.util;

import org.springframework.util.DigestUtils;

import java.util.UUID;

/**
 * @Author HanSiyue
 * @Date 2019/9/18 下午3:43
 */
public class TokenUtil {

    /**
     * 生成token (Md5Token)
     * @param strings
     * @return
     */
    public static String generateToken(String... strings){
        long timestamp = System.currentTimeMillis();
        String tokenMeta = "";
        for (String s : strings) {
            tokenMeta = tokenMeta + s;
        }
        tokenMeta = tokenMeta + timestamp;
        String token = DigestUtils.md5DigestAsHex(tokenMeta.getBytes());
        return token;
    }
}