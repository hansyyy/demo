package com.example.SSO.interceptor;

import com.example.SSO.annotation.AuthToken;
import com.example.SSO.constant.ConstantKit;
import com.example.SSO.util.ResultUtil;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @Author HanSiyue
 * @Date 2019/9/18 下午8:07
 */
public class AuthorizationInterceptor implements HandlerInterceptor {

    //存放鉴权信息的Header名称，默认是Authorization
    private String httpHeaderName = "Authorization";

    /**
     * 存放登录用户模型Key的Request Key
     */
    public static final String REQUEST_CURRENT_KEY = "REQUEST_CURRENT_KEY";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        // 如果打上了AuthToken注解则需要验证token
        if (method.getAnnotation(AuthToken.class) != null || handlerMethod.getBeanType().getAnnotation(AuthToken.class) != null) {

            String token = request.getParameter(httpHeaderName);
            String username = "";
            Jedis jedis = new Jedis("127.0.0.1", 6379);
            if (token != null && token.length() != 0) {
                username = jedis.get(token);
            }
            if (username != null && !("").equals(username.trim())) {
                Long tokeBirthTime = Long.valueOf(jedis.get(token + username));
                Long diff = System.currentTimeMillis() - tokeBirthTime;
                //重新设置Redis中的token过期时间
                if (diff > ConstantKit.TOKEN_RESET_TIME) {
                    jedis.expire(username, ConstantKit.TOKEN_EXPIRE_TIME);
                    jedis.expire(token, ConstantKit.TOKEN_EXPIRE_TIME);
                    Long newBirthTime = System.currentTimeMillis();
                    jedis.set(token + username, newBirthTime.toString());
                }

                //用完关闭
                jedis.close();
                request.setAttribute(REQUEST_CURRENT_KEY, username);
                return true;
            } else {
                try {
                    System.out.println(ResultUtil.error());
                    return false;
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        }

        request.setAttribute(REQUEST_CURRENT_KEY, null);

        return true;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
