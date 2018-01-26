package com.gtja.shiee.topic.web.rest;

import com.gtja.shiee.topic.common.entity.Comment;
import com.gtja.shiee.topic.common.model.Result;
import com.gtja.shiee.topic.util.SessionUtil;
import com.gtja.shiee.topic.common.exception.ResultException;
import com.gtja.shiee.topic.service.CommentService;
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
    @PostMapping("del")
    public Result del(HttpServletRequest request, String commentId) throws ResultException {
        Comment comment = commentService.del(commentId, SessionUtil.getUser(request));
        return Result.success().add("comment", comment);
    }
}
