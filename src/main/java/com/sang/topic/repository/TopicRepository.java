package com.sang.topic.repository;


import com.sang.topic.common.entity.Topic;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface TopicRepository extends MongoRepository<Topic, String> {
    @Query("{'parentId':?0 , 'available':?1}")
    List<Topic> findByParentIdAndAvailable(String parentId, Integer available);
    List<Topic> findByIdIn(List<String> ids);
}
