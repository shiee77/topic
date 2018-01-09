package com.sang.topic.service.impl;


import com.sang.topic.common.constants.CommonConstants;
import com.sang.topic.common.constants.MessageConstants;
import com.sang.topic.common.constants.ResultConstants;
import com.sang.topic.common.entity.Topic;
import com.sang.topic.common.exception.ResultException;
import com.sang.topic.common.model.Page;
import com.sang.topic.common.model.TreeView;
import com.sang.topic.repository.TopicRepository;
import com.sang.topic.service.PostService;
import com.sang.topic.service.TopicService;
import com.sang.topic.util.TopicStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


import javax.management.Query;
import java.util.*;

@Service
public class TopicServiceImpl implements TopicService {
    @Autowired
    TopicRepository topicRepository;
    @Autowired
    PostService postService;

    /**
        @author : liujx
        @description : 展示所有的板块列表，板块下又可能有板块数据
        @description : 或者展示所有的帖子列表
        @date : Create in 上午10:18 2018/1/4

    **/
    @Override
    public void getWithPageType(String topicId, Page page, Map<String, Object> model) throws ResultException {
        //根据id查询是否有对应的板块（或者市场）信息
        Topic topic = topicRepository.findOne(topicId);
        //如果为空，则抛出异常
        if(topic == null)
            throw new ResultException(MessageConstants.TOPIC_NOT_FOUND, ResultConstants.NOT_FOUND);

        if(topic.getPageType() == CommonConstants.PageType.SHOW_CHILD_TOPIC){
            //如果该板块下还有子版块信息,则将所有的子版块信息展示
            model.put("childTopics", this.getChildren(topicId));
        }else if(topic.getPageType() == CommonConstants.PageType.SHOW_POST){
            //如果该板块下没有子版块信息,则展示具体的帖子列表
            if(topic.getSecNav() == CommonConstants.SecNav.BROTHER){
                model.put("nav2", this.getBrother(topicId));
            }
            model.put("topicShowTypes", TopicStringUtils.toIntegerList(topic.getPostShowTypes()));
            model.put("posts", postService.getByTopicId(topicId, page));
            model.put("page", page);
        }
        model.put("topicId", topicId);
    }

    @Transactional
    @Override
    public Topic add(String name, String parentId) throws ResultException {
        Topic topic = new Topic();
        topic.setName(name);
        topic.setAvailable(1);
        topic.setParentId(parentId);
        topic.setOrderType(CommonConstants.OrderType.DEFAULT);
        topic.setPageType(CommonConstants.PageType.DEFAULT);
        topic.setPostShowTypes(CommonConstants.PostShowTypes.DEFAULT);
        topic.setSecNav(CommonConstants.SecNav.DEFAULT);
        //如果有父节点
        if (!StringUtils.isEmpty(parentId) && !parentId.equals("0")) {
            topic.setLevelId(0);
            Topic parent = topicRepository.findOne(parentId);
            if (parent == null)
                throw new ResultException(MessageConstants.TOPIC_NOT_FOUND, ResultConstants.NOT_FOUND);
            if (parent.isRoot())
                topic.setParentIds(parentId.toString());
            else
                topic.setParentIds(parent.getParentIds() + "," + parentId);
            //如果有上级节点,且未设置成展示子节点状态,则将上级节点的pagetype设置为展示展示子节点状态
            if(parent.getPageType() != CommonConstants.PageType.SHOW_CHILD_TOPIC){
                parent.setPageType(CommonConstants.PageType.SHOW_CHILD_TOPIC);
                topicRepository.save(parent);
            }
        }else{
            topic.setLevelId(1);
        }
        topicRepository.save(topic);
        return topic;
    }

    @Override
    public Topic get(String id) throws ResultException {
        Topic topic = topicRepository.findOne(id);
        if (topic == null)
            throw new ResultException(MessageConstants.TOPIC_NOT_FOUND, ResultConstants.NOT_FOUND);
        return topic;
    }

    @Override
    public List<Topic> getChildren(String id) throws ResultException {
        List<Topic> list = topicRepository.findByParentIdAndAvailable(id, CommonConstants.Available.AVAILABLE);
        return list;
    }

    @Override
    public List<Topic> getBrother(String id) throws ResultException {
        Topic topic = topicRepository.findOne(id);
        if (topic == null)
            throw new ResultException(MessageConstants.TOPIC_NOT_FOUND, ResultConstants.NOT_FOUND);
        Topic parentTopic = topicRepository.findOne(topic.getParentId());
        if (parentTopic == null)
            return new ArrayList<>();
        List<Topic> list = topicRepository.findByParentIdAndAvailable(parentTopic.getId(), CommonConstants.Available.AVAILABLE);
        return list;
    }

    @Override
    public List<Topic> getParentTopic(String topicId) {
        Topic t = topicRepository.findOne(topicId);
        List<String> idList = new ArrayList<>();
        String[] ids = t.getParentIds().split(",");

        for (String id : ids) {
            idList.add(id);
        }
        List<Topic> list = topicRepository.findByIdIn(idList);
        list.add(t);
        return list;
    }

    @Override
    public List<TreeView> getTreeView() {
        TreeView root = new TreeView();
        List<Topic> list = topicRepository.findAll();
        list.forEach(t -> {
            if (t.isRoot()) {
                root.setId(t.getId());
                root.setText(t.getName());
            } else {
                root.addNode(t.getParentIds(), t.getId(), t.getName());
            }
        });
        List<TreeView> result = new ArrayList<>();
        result.add(root);
        return result;
    }

    @Override
    public Topic save(String topicId, String name, Integer available, Integer orderType) throws ResultException {
        Topic topic = topicRepository.findOne(topicId);
        if (topic == null)
            throw new ResultException(MessageConstants.TOPIC_NOT_FOUND, ResultConstants.NOT_FOUND);
//        topic.setName(name);
        topic.setAvailable(available);
        topic.setOrderType(orderType);
        return this.save(topic);
    }

    @Override
    public Topic save(Topic topic) throws ResultException {
        Topic t = topicRepository.findOne(topic.getId());
        t.setPageType(topic.getPageType());
        t.setOrderType(topic.getOrderType());
        t.setAvailable(topic.getAvailable());
        t.setName(topic.getName());
        t.setPostShowTypes(topic.getPostShowTypes());
        t.setSecNav(topic.getSecNav());
        return topicRepository.save(t);
    }

    @Override
    public List<Topic> getFirstLevel() {

        return topicRepository.findByParentIdAndAvailable("0" , CommonConstants.Available.AVAILABLE);
    }
}
