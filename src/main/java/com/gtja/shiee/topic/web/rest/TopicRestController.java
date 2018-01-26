package com.gtja.shiee.topic.web.rest;

import com.gtja.shiee.topic.common.model.Page;
import com.gtja.shiee.topic.service.PostService;
import com.gtja.shiee.topic.service.TopicService;
import com.gtja.shiee.topic.common.entity.Post;
import com.gtja.shiee.topic.common.entity.Topic;
import com.gtja.shiee.topic.common.exception.ResultException;
import com.gtja.shiee.topic.common.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/rest/t")
public class TopicRestController {
    @Autowired
    TopicService topicService;
    @Autowired
    PostService postService;

    @GetMapping("/{topicId}")
    public Result get(@PathVariable String topicId) throws ResultException {
        Topic topic = topicService.get(topicId);
        List<Topic> topics = topicService.getChildren(topicId);
        return Result.success().add("topic", topic)
                .add("childTopics", topics);
    }

    @GetMapping("/{topicId}/p")
    public Result getPosts(@PathVariable String topicId, Page page) {
        List<Post> posts = postService.getByTopicId(topicId, page);
        return Result.success()
                .add("posts", posts)
                .add("page", page);
    }

    @PostMapping("")
    public Result add(String name, String parentId) throws ResultException {
        Topic topic = topicService.add(name, parentId);
        return Result.success().add("topic", topic);
    }
}
