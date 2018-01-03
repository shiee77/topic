package com.sang.topic.repository;


import com.sang.topic.common.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CommentRepository extends MongoRepository<Comment, Integer> {
    Page<Comment> findByPostId(int topicId, Pageable pageable);
}