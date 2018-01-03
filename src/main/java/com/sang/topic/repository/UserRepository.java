package com.sang.topic.repository;


import com.sang.topic.common.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, Integer> {
    User findByUsername(String username);

    User findByUsernameAndPassword(String username, String password);
}
