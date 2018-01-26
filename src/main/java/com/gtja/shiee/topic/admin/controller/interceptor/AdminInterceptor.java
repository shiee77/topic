package com.gtja.shiee.topic.admin.controller.interceptor;

import com.gtja.shiee.topic.common.entity.User;
import com.gtja.shiee.topic.util.SessionUtil;
import com.gtja.shiee.topic.common.constants.CommonConstants;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by sh on 2017/4/2.
 */
public class AdminInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        User user = SessionUtil.getUser(request);
        if(user == null || user.getRoleId() != CommonConstants.Role.SUPER_ADMIN) {
            response.sendRedirect(request.getContextPath()+"/400");
            return true;
        }
        return super.preHandle(request, response, handler);
    }
}
