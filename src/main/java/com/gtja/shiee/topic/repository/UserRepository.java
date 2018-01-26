package com.gtja.shiee.topic.repository;


import com.gtja.shiee.topic.common.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {
    List<User> findByUsername(String username);

    List<User> findByUsernameAndPassword(String username, String password);
}
