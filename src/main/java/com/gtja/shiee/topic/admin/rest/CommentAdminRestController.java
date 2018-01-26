package com.gtja.shiee.topic.admin.rest;

import com.gtja.shiee.topic.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/admin/rest/c")
public class CommentAdminRestController {
    @Autowired
    CommentService commentService;
}
