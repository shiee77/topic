package com.gtja.shiee.topic.service;


import com.gtja.shiee.topic.common.entity.User;
import com.gtja.shiee.topic.common.entity.Post;
import com.gtja.shiee.topic.common.exception.ResultException;
import com.gtja.shiee.topic.common.model.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PostService {
    List<Post> getAll(Page page);

    List<Post> getByTopicId(String topicId, Page page);

    Post get(String id, User user, boolean flag) throws ResultException;

    Post add(String title, String content, String topicId, User user) throws ResultException;

    @Transactional
    Post save(Post post);

    Post del(String postId, User user) throws ResultException;

    Post liked(String postId, User user) throws ResultException;

    Post cancelLiked(String postId, User user) throws ResultException;

    Integer getIsUserLiked(String postId, User user) throws ResultException;

    List<Post> getPostByIds(List<String> ids);

    List<Post> getTopicAll(Page page);

    List<Post> getTopicAllBySearchInfo(String searchInfo, Page page);

    List<Post>  getByTopicIdAndSearchInfo(String topicId, String searchInfo, Page page);
}
