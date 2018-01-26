package com.gtja.shiee.topic.admin.rest;

import com.gtja.shiee.topic.common.entity.Topic;
import com.gtja.shiee.topic.common.exception.ResultException;
import com.gtja.shiee.topic.common.model.Result;
import com.gtja.shiee.topic.service.PostService;
import com.gtja.shiee.topic.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/admin/rest/t")
public class TopicAdminRestController {
    @Autowired
    TopicService topicService;
    @Autowired
    PostService postService;

    @GetMapping("/tree")
    public Result getTreeView() {
        return Result.success().add("tree", topicService.getTreeView());
    }

    @PutMapping("/{topicId}")
    public Result saveTopic(@PathVariable String topicId, Topic topic) throws ResultException {
        return Result.success().add("topic", topicService.save(topic));
    }

    @GetMapping("/{topicId}")
    public Result get(@PathVariable String topicId) throws ResultException {
        return Result.success().add("topic", topicService.get(topicId));
    }

    @PostMapping("")
    public Result add(String parentId, String name) throws ResultException {
        return Result.success().add("topic", topicService.add(name, parentId));
    }
}
