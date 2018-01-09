package com.sang.topic.web.controller;

import com.sang.topic.common.model.Page;
import com.sang.topic.service.PostService;
import com.sang.topic.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
public class IndexController {
    @Autowired
    PostService postService;
    @Autowired
    TopicService topicService;

    /**
        @author : liujx
        @description : 登录
        @date : Create in 下午8:47 2018/1/7

    **/
    @GetMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("web/login");
    }

    /**
        @author : liujx
        @description : 注册
        @date : Create in 下午8:47 2018/1/7

    **/
    @GetMapping("/register")
    public ModelAndView register() {
        return new ModelAndView("web/register");
    }

    /**
        @author : liujx
        @description : 进入首页
        @date : Create in 下午8:47 2018/1/7

    **/
    @GetMapping("")
    public ModelAndView index(Map<String, Object> model, Page page) {
        return new ModelAndView("web/index");
    }

    /**
        @author : liujx
        @description : 错误400进入
        @date : Create in 下午8:48 2018/1/7

    **/
    @RequestMapping("/400")
    public ModelAndView error400(Map<String, Object> model){
        model.put("status", 400);
        model.put("message", "错误的请求方式");
        return new ModelAndView("error");
    }

    /**
        @author : liujx
        @description : 错误404进入
        @date : Create in 下午8:48 2018/1/7

    **/
    @RequestMapping("/404")
    public ModelAndView error404(Map<String, Object> model){
        model.put("status", 404);
        model.put("message", "找不到页面");
        return new ModelAndView("error");
    }

    /**
        @author : liujx
        @description : 错误500进入
        @date : Create in 下午8:48 2018/1/7

    **/
    @RequestMapping("/500")
    public ModelAndView error500(Map<String, Object> model){
        model.put("status", 500);
        model.put("message", "服务器发生错误");
        return new ModelAndView("error");
    }
}
