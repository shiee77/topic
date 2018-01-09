package com.sang.topic.web.controller;

import com.sang.topic.common.exception.ResultException;
import com.sang.topic.common.model.Page;
import com.sang.topic.service.PostService;
import com.sang.topic.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequestMapping("/t")
public class TopicController {
    @Autowired
    TopicService topicService;

    /**
        @author : liujx
        @description : 帖子帖子列表展示进入页面（包括一级列表、二级列表、帖子列表
        @// TODO: 2018/1/7 需要更改，一级列表需要和二级列表同时显示在同一个页面上
        @date : Create in 下午8:49 2018/1/7

    **/
    @GetMapping("/{topicId}")
    public ModelAndView topic(@PathVariable String topicId, Page page, Map<String, Object> model)
            throws ResultException {
        topicService.getWithPageType(topicId, page, model);
        return new ModelAndView("web/topic");
    }
}
