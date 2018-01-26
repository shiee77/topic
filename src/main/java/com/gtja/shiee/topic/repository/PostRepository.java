package com.gtja.shiee.topic.repository;


import com.gtja.shiee.topic.common.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface PostRepository extends MongoRepository<Post, String> {
    Page<Post> findByTopicId(String topicId, Pageable pageable);

    List<Post> findByIdIn(List<String> ids);

    Page<Post> findByTitleLike(String searchInfo, Pageable pageable);


    Page<Post> findByTopicIdAndTitleLike(String topicId, String searchInfo, Pageable pageable);
}
