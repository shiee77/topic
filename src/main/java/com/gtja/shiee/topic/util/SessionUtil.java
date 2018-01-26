package com.gtja.shiee.topic.util;

import com.gtja.shiee.topic.common.entity.User;

import javax.servlet.http.HttpServletRequest;

/**
    @author : liujx
    @description : 
    @date : Create in 下午5:21 2018/1/10

**/
public class SessionUtil {
    private static final String SESSION_USER = "sessionUser";
    public static User addUser(HttpServletRequest request, User user) {
        request.getSession().setAttribute(SESSION_USER, user);
        return user;
    }

    public static void removeUser(HttpServletRequest request) {
        request.getSession().removeAttribute(SESSION_USER);
        request.getSession().invalidate();
    }

    public static User getUser(HttpServletRequest request) {
        return (User) request.getSession().getAttribute(SESSION_USER);
    }
}
