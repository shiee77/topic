package com.gtja.shiee.topic.admin.rest;

import com.gtja.shiee.topic.service.PostService;
import com.gtja.shiee.topic.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/admin/rest/p")
public class PostAdminRestController {
    @Autowired
    PostService postService;
    @Autowired
    CommentService commentService;
}
