package com.gtja.shiee.topic.service.impl;

import com.gtja.shiee.topic.common.constants.MessageConstants;
import com.gtja.shiee.topic.common.entity.Post;
import com.gtja.shiee.topic.common.entity.User;
import com.gtja.shiee.topic.common.entity.UserLiked;
import com.gtja.shiee.topic.common.exception.ResultException;
import com.gtja.shiee.topic.repository.UserLikedRepository;
import com.gtja.shiee.topic.service.PostService;
import com.gtja.shiee.topic.service.UserLikedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserLikedServiceImpl implements UserLikedService {
    @Autowired
    UserLikedRepository userLikedRepository ;
    @Autowired
    PostService postService;
    /**
        @author : liujx
        @description : 查询用户所有的浏览记录信息
        @date : Create in 上午10:40 2018/1/15
    
    **/
    @Override
    public List<Post> findScanInfo(User user) throws  ResultException {
        if(user == null)
            throw new ResultException(MessageConstants.USER_LOGIN_REQUIRE);

        List<UserLiked> userLikeds = userLikedRepository.findByUserId(user.getId());

        List<String> ids = new ArrayList<>();
        if(userLikeds != null){
            userLikeds.forEach(userLiked -> {
                ids.add(userLiked.getPostId());
            });
        }

        List<Post> posts = postService.getPostByIds(ids);

        return posts;
    }

    /**
        @author : liujx
        @description : 查询用户关注记录
        @date : Create in 上午11:35 2018/1/15
    
    **/
    @Override
    public List<Post> findFollowing(User user) throws ResultException {
        if(user == null)
            throw new ResultException(MessageConstants.USER_LOGIN_REQUIRE);

        List<UserLiked> userLikeds = userLikedRepository.findByUserId(user.getId());

        List<String> ids = new ArrayList<>();
        if(userLikeds != null){
            userLikeds.forEach(userLiked -> {
                if(userLiked.isLiked() || userLiked.isFollowing())
                  ids.add(userLiked.getPostId());
            });
        }

        List<Post> posts = postService.getPostByIds(ids);

        return posts;
    }
}
