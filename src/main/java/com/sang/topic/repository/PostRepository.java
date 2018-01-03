package com.sang.topic.repository;


import com.sang.topic.common.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PostRepository extends MongoRepository<Post, Integer> {
    Page<Post> findByTopicId(Integer topicId, Pageable pageable);

    Long countByTopicId(Integer topicId);
}
