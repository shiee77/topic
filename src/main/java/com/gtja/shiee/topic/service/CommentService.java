package com.gtja.shiee.topic.service;


import com.gtja.shiee.topic.common.entity.Comment;
import com.gtja.shiee.topic.common.entity.User;
import com.gtja.shiee.topic.common.exception.ResultException;
import com.gtja.shiee.topic.common.model.Page;

import java.util.List;

public interface CommentService {
    List<Comment> getByPostIdAndPage(String topicId, Page page);

    Comment add(String content, String postId,String commentId, User user) throws ResultException;
    Comment del(String commentId, User user) throws ResultException;
}
