package com.gtja.shiee.topic.web.controller;

import com.gtja.shiee.topic.common.entity.Post;
import com.gtja.shiee.topic.common.entity.User;
import com.gtja.shiee.topic.common.entity.UserLiked;
import com.gtja.shiee.topic.service.UserLikedService;
import com.gtja.shiee.topic.service.UserService;
import com.gtja.shiee.topic.common.exception.ResultException;
import com.gtja.shiee.topic.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/u")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    UserLikedService userLikedService;

    /**
        @author : liujx
        @description : 进入用户信息页面,查询用户浏览记录
        @// TODO: 2018/1/7 未完成此页面功能，需要添加列表展示相关的浏览记录信息、回复记录信息等等
        @date : Create in 下午8:52 2018/1/7

    **/
    @GetMapping("/{userId}")
    public ModelAndView index(@PathVariable String userId, Map<String, Object> model) throws ResultException {
        User user = userService.get(userId);
        model.put("user", user);
        //加载浏览信息
       List<Post> posts =  userLikedService.findScanInfo(user);
       model.put("posts",posts);
       model.put("title",0);
        return new ModelAndView("web/user");
    }

    /**
        @author : liujx
        @description : 查询用户关注记录
        @date : Create in 上午11:34 2018/1/15
    
    **/
    @GetMapping("/following/{userId}")
    public ModelAndView scan(@PathVariable String userId, Map<String, Object> model) throws ResultException {
        User user = userService.get(userId);
        model.put("user", user);
        //加载浏览信息
       List<Post> posts =  userLikedService.findFollowing(user);
       model.put("posts",posts);
       model.put("title",1);
        return new ModelAndView("web/user");
    }
}
