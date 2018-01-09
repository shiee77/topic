package com.sang.topic.service.impl;


import com.sang.topic.common.constants.CommonConstants;
import com.sang.topic.common.constants.MessageConstants;
import com.sang.topic.common.constants.ResultConstants;
import com.sang.topic.common.entity.User;
import com.sang.topic.common.exception.ResultException;
import com.sang.topic.common.model.Page;
import com.sang.topic.repository.UserRepository;
import com.sang.topic.service.UserService;
import com.sang.topic.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;



    /**
        @author : liujx
        @description : 添加用户信息
        @date : Create in 上午9:45 2018/1/4

    **/
    @Transactional
    @Override
    public User add(User user) {
        if (user.getRoleId() == null)
            user.setRoleId(CommonConstants.Role.NORMAL);
        if (user.getAvailable() == null)
            user.setAvailable(CommonConstants.Available.AVAILABLE);
        user.setCreateTime(new Date());
        return userRepository.save(user);
    }

    /**
        @author : liujx
        @description : 注册用户
        @date : Create in 上午9:45 2018/1/4

    **/
    @Transactional
    @Override
    public User register(String username, String password) throws ResultException {
        List<User> userList = userRepository.findByUsername(username);
        //判断是否存在相同的用户
        if ( userList != null && userList.size() > 0)
            throw new ResultException(MessageConstants.USER_CREATE_REPEAT);
        User user = new User();
        user.setUsername(username);
        user.setPassword(SecurityUtil.encryptPassword(password));
        return this.add(user);
    }

    /**
        @author : liujx
        @description : 系统登录
        @date : Create in 上午10:00 2018/1/4

    **/
    @Override
    public User login(String username, String password) throws ResultException {
        List<User> userList = userRepository.findByUsernameAndPassword(username,
                SecurityUtil.encryptPassword(password));
        if (userList == null || userList.size() <= 0)
            throw new ResultException(MessageConstants.USER_LOGIN_FAIL);
        return userList.get(0);
    }

    /**
        @author : liujx
        @description : 根据用户id查找用户信息
        @date : Create in 上午10:01 2018/1/4

    **/
    @Override
    public User get(String id) throws ResultException {
        User user = userRepository.findOne(id);
        if (user == null)
            throw new ResultException(MessageConstants.USER_NOT_FOUND, ResultConstants.NOT_FOUND);
        return user;
    }

    /**
        @author : liujx
        @description : 根据用户姓名查找用户信息
        @date : Create in 上午10:01 2018/1/4

    **/
    @Override
    public User getByUsername(String username) {
        return userRepository.findByUsername(username).get(0);
    }

    /**
        @author : liujx
        @description : 查找所有的用户数据信息
        @date : Create in 上午10:02 2018/1/4

    **/
    @Override
    public List<User> getAll(Page page) {
        org.springframework.data.domain.Page<User> p = userRepository.findAll(page.toPageable());
        page.setCount(p.getTotalElements());
        return p.getContent();
    }

    /**
        @author : liujx
        @description : 查找用户的数量
        @date : Create in 上午10:04 2018/1/4

    **/
    @Override
    public long getCount() {
        return userRepository.count();
    }

    /**
        @author : liujx
        @description : 更新用户信息
        @date : Create in 上午10:09 2018/1/4

    **/
    @Override
    public User save(User user) throws ResultException {
        User u = userRepository.findOne(user.getId());
        if(u == null)
            throw new ResultException(MessageConstants.USER_NOT_FOUND, ResultConstants.NOT_FOUND);
        u.setRoleId(user.getRoleId());
        return userRepository.save(u);
    }
}
