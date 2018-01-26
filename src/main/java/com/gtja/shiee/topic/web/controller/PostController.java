package com.gtja.shiee.topic.web.controller;

import com.gtja.shiee.topic.common.model.Page;
import com.gtja.shiee.topic.service.PostService;
import com.gtja.shiee.topic.service.TopicService;
import com.gtja.shiee.topic.util.SessionUtil;
import com.gtja.shiee.topic.common.entity.Post;
import com.gtja.shiee.topic.common.exception.ResultException;
import com.gtja.shiee.topic.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/p")
public class PostController {
    @Autowired
    TopicService topicService;
    @Autowired
    PostService postService;
    @Autowired
    CommentService commentService;

    /**
        @author : liujx
        @description : 帖子详情页进入位置
        @date : Create in 下午8:58 2018/1/7

    **/
    @GetMapping("/{postId}")
    public ModelAndView post(@PathVariable String postId, Page page, Map<String, Object> model, HttpServletRequest request) throws ResultException {
        Post post = postService.get(postId,SessionUtil.getUser(request),true);
        model.put("post", post);
        model.put("comments", commentService.getByPostIdAndPage(postId, page));
        model.put("postId", postId);
        model.put("parentTopic", topicService.getParentTopic(post.getTopicId()));
        if(SessionUtil.getUser(request) != null)
            model.put("isUserLiked",postService.getIsUserLiked(postId, SessionUtil.getUser(request)));
        return new ModelAndView("web/post", model);
    }
}
