package com.lehui.config;

import com.lehui.utils.Constants;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.context.support.HttpRequestHandlerServlet;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.ws.WebServiceException;
import java.awt.*;

/**
 * 身份验证
 * Created by SunHaiyang on 2017/6/7.
 */
@Component
@Log4j
public class LoginInterceptor implements HandlerInterceptor,Constants {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        if(!o.getClass().isAssignableFrom(HandlerMethod.class)){
            return true;
        }
        HttpSession session = httpServletRequest.getSession();
        System.out.println(session.getId());
        try {
            String sessionToken = session.getAttribute(SESSION_TOKEN_KEY).toString();
            String token = httpServletRequest.getHeader(SESSION_TOKEN_KEY);
            if(sessionToken.equals(token)){
                log.info("身份验证成功");
                return true;
            }
            log.info("身份验证失败");
            return false;
        }catch (NullPointerException e){
            return false;
        }

    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }
}
