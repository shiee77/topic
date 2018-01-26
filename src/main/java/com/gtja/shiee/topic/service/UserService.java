package com.gtja.shiee.topic.service;


import com.gtja.shiee.topic.common.entity.User;
import com.gtja.shiee.topic.common.exception.ResultException;
import com.gtja.shiee.topic.common.model.Page;

import java.util.List;

public interface UserService {
    User register(String username, String password) throws ResultException;

    User login(String username, String password) throws ResultException;

    User add(User user);

    User get(String id) throws ResultException;

    User getByUsername(String username);

    List<User> getAll(Page page);

    long getCount();

    User save(User user) throws ResultException;
}
