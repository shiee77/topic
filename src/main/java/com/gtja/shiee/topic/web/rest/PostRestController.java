package com.gtja.shiee.topic.web.rest;

import com.gtja.shiee.topic.common.model.Page;
import com.gtja.shiee.topic.service.PostService;
import com.gtja.shiee.topic.util.SessionUtil;
import com.gtja.shiee.topic.common.entity.Comment;
import com.gtja.shiee.topic.common.entity.Post;
import com.gtja.shiee.topic.common.exception.ResultException;
import com.gtja.shiee.topic.common.model.Result;
import com.gtja.shiee.topic.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@RequestMapping("/rest/p")
public class PostRestController {
    @Autowired
    PostService postService;
    @Autowired
    CommentService commentService;

    @GetMapping("/{postId}")
    public Result get(@PathVariable String postId) throws ResultException {
        Post post = postService.get(postId,null, false);
        return Result.success().add("post", post);
    }

    @GetMapping("/{postId}/c")
    public Result get(@PathVariable String postId, Page page) {
        List<Comment> comments = commentService.getByPostIdAndPage(postId, page);
        return Result.success().add("post", comments);
    }

    @PostMapping("")
    public Result add(HttpServletRequest request, String title, String content, String topicId) throws ResultException {
        Post post = postService.add(title, content, topicId, SessionUtil.getUser(request));
        return Result.success().add("post", post);
    }

    @PostMapping("/del")
    public Result del(HttpServletRequest request, String postId) throws ResultException {
        Post post = postService.del(postId, SessionUtil.getUser(request));
        return Result.success().add("post", post);
    }
    @PostMapping("/liked")
    public Result liked(HttpServletRequest request, String postId) throws ResultException {
        Post post = postService.liked(postId, SessionUtil.getUser(request));
        return Result.success().add("post", post);
    }
    @PostMapping("/cancelLiked")
    public Result cancelLiked(HttpServletRequest request, String postId) throws ResultException {
        Post post = postService.cancelLiked(postId, SessionUtil.getUser(request));
        return Result.success().add("post", post);
    }
}
