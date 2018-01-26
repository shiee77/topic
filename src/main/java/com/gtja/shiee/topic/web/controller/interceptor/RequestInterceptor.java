package com.gtja.shiee.topic.web.controller.interceptor;

import com.gtja.shiee.topic.common.entity.Topic;
import com.gtja.shiee.topic.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Component
public class RequestInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    TopicService topicService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        List<Topic> topics = topicService.getFirstLevel();
        request.setAttribute("topNav", topics);
        request.setAttribute("basePath", request.getContextPath());
        request.setAttribute("url", request.getRequestURL());
        return super.preHandle(request, response, handler);
    }
}
