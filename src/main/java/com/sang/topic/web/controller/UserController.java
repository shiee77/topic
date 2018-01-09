package com.sang.topic.web.controller;

import com.sang.topic.common.exception.ResultException;
import com.sang.topic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequestMapping("/u")
public class UserController {
    @Autowired
    UserService userService;

    /**
        @author : liujx
        @description : 进入用户信息页面
        @// TODO: 2018/1/7 未完成此页面功能，需要添加列表展示相关的浏览记录信息、回复记录信息等等
        @date : Create in 下午8:52 2018/1/7

    **/
    @GetMapping("/{userId}")
    public ModelAndView index(@PathVariable String userId, Map<String, Object> model) throws ResultException {
        model.put("user", userService.get(userId));
        return new ModelAndView("web/user");
    }
}
