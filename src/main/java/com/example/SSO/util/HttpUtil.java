package com.example.SSO.util;

import org.springframework.util.StreamUtils;

import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * @Author HanSiyue
 * @Date 2019/9/18 下午3:41
 */
public class HttpUtil {

    public static String sendHttpRequest (String httpUrl, Map<String,String> params) throws Exception{
        URL url = new URL(httpUrl);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setDoOutput(true);
        if (params!=null&&params.size()>0){
            StringBuilder stringBuilder = new StringBuilder();
            for(Map.Entry<String,String>entry:params.entrySet()){
                stringBuilder.append("&").append(entry.getKey()).append("=").append(entry.getValue());
            }
            httpURLConnection.getOutputStream().write(stringBuilder.substring(1).toString().getBytes("utf-8"));
            httpURLConnection.connect();
            String str = StreamUtils.copyToString(httpURLConnection.getInputStream(), Charset.forName("UTF-8"));
            return str;
        }else {
            return null;
        }
    }
}