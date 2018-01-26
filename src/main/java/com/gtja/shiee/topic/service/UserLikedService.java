package com.gtja.shiee.topic.service;



import com.gtja.shiee.topic.common.entity.Post;
import com.gtja.shiee.topic.common.entity.User;
import com.gtja.shiee.topic.common.exception.ResultException;

import java.util.List;

public interface UserLikedService {

    List<Post> findScanInfo(User user) throws ResultException;

    List<Post> findFollowing(User user) throws ResultException;
}
