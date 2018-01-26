package com.gtja.shiee.topic.service;


import com.gtja.shiee.topic.common.entity.Topic;
import com.gtja.shiee.topic.common.exception.ResultException;
import com.gtja.shiee.topic.common.model.Page;
import com.gtja.shiee.topic.common.model.TreeView;

import java.util.List;
import java.util.Map;

public interface TopicService {
    void getWithPageType(String topicId, String searchInfo, Page page, Map<String, Object> model) throws ResultException;

    Topic add(String name, String parentId) throws ResultException;

    Topic get(String id) throws ResultException;

    List<Topic> getChildren(String id) throws ResultException;

    List<Topic> getBrother(String id) throws ResultException;

    List<Topic> getParentTopic(String topicId);

    List<TreeView> getTreeView();

    Topic save(String topicId, String name, Integer available, Integer orderType) throws ResultException;

    Topic save(Topic topic) throws ResultException;

    List<Topic> getFirstLevel();
}
