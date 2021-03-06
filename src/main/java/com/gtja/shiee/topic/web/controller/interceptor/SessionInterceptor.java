package com.gtja.shiee.topic.web.controller.interceptor;

import com.gtja.shiee.topic.common.entity.User;
import com.gtja.shiee.topic.util.SessionUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class SessionInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        User user = SessionUtil.getUser(request);
        if(user == null)
            response.sendRedirect(request.getContextPath()+"/login");
        return super.preHandle(request, response, handler);
    }
}
