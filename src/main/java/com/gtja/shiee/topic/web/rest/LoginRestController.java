package com.gtja.shiee.topic.web.rest;

import com.gtja.shiee.topic.common.entity.User;
import com.gtja.shiee.topic.common.exception.ResultException;
import com.gtja.shiee.topic.common.model.Result;
import com.gtja.shiee.topic.util.SessionUtil;
import com.gtja.shiee.topic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/rest")
public class LoginRestController {
    @Autowired
    UserService userService;

    /**
        @author : liujx
        @description : 注册
        @date : Create in 下午8:46 2018/1/7

    **/
    @PostMapping("/register")
    public Result register(HttpServletRequest request, String username, String password) throws ResultException {
        User user = userService.register(username, password);
        Result result = Result.success();
        result.add("user", user);
        result.add("jsessionid", request.getSession().getId());
        SessionUtil.addUser(request, (User) result.get("user"));
        return result;
    }

    /**
        @author : liujx
        @description : 登录
        @date : Create in 下午8:45 2018/1/7

    **/
    @PostMapping("/login")
    public Result login(HttpServletRequest request, String username, String password) throws ResultException {
        User user = userService.login(username, password);
        Result result = Result.success();
        result.add("user", user);
        result.add("jsessionid", request.getSession().getId());
        SessionUtil.addUser(request, (User) result.get("user"));
        return result;
    }

    /**
        @author : liujx
        @description : 注销
        @date : Create in 下午8:45 2018/1/7

    **/
    @PostMapping("/logout")
    public Result logout(HttpServletRequest request) {
        SessionUtil.removeUser(request);
        return Result.success();
    }
}
