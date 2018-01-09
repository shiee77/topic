package com.sang.topic.service;


import com.sang.topic.common.entity.Topic;
import com.sang.topic.common.exception.ResultException;
import com.sang.topic.common.model.Page;
import com.sang.topic.common.model.TreeView;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface TopicService {
    void getWithPageType(String topicId, Page page, Map<String, Object> model) throws ResultException;

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
