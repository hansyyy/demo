package com.example.SSO.config;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @Author HanSiyue
 * @Date 2019/12/2 下午9:10
 */
@Component
public class CorsFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String[] allowDomain = {"http://192.168.137.1:8080","http://192.168.43.26:8080","http://172.22.1.161:8080","http://10.83.160.184：8080"};
        Set allowedOrigins = new HashSet(Arrays.asList(allowDomain));
        String originHeader = request.getHeader("Origin");

        if (allowedOrigins.contains(originHeader)) {
            response.setHeader("Access-Control-Allow-Origin", originHeader);
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PATCH, PUT");
            response.setHeader("Access-Control-Max-Age", "3600");
            response.setHeader("Access-Control-Allow-Headers", "Origin, No-Cache, X-Requested-With," +
                    " If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With,userId,token");
            response.setHeader("XDomainRequestAllowed", "1");
        }

        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() { }

}
