package com.sang.topic.repository;


import com.sang.topic.common.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TopicRepository extends MongoRepository<Topic, Integer> {
    List<Topic> findByParentIdAndAvailable(Integer parentId, Integer available);

    List<Topic> findByIdIn(List<Integer> parentIds);
}
