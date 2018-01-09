package com.sang.topic.web.rest;

import com.sang.topic.common.entity.Comment;
import com.sang.topic.common.exception.ResultException;
import com.sang.topic.common.model.Result;
import com.sang.topic.service.CommentService;
import com.sang.topic.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/rest/c")
public class CommentRestController {
    @Autowired
    CommentService commentService;

    /**
        @author : liujx
        @description : 发表回复信息
        @date : Create in 上午10:13 2018/1/8

    **/
    @PostMapping("")
    public Result add(HttpServletRequest request, String postId, String commentId, String content) throws ResultException {
        Comment comment = commentService.add(content, postId, commentId, SessionUtil.getUser(request));
        return Result.success().add("comment", comment);
    }
}
