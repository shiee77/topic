package com.gtja.shiee.topic.repository;


import com.gtja.shiee.topic.common.entity.UserLiked;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface UserLikedRepository extends MongoRepository<UserLiked, String> {
    List<UserLiked> findByUserIdAndPostId(String userId, String postId);

    List<UserLiked> findByUserId(String userId);
}
