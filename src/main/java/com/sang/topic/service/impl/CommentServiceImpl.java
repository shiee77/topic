package com.sang.topic.service.impl;


import com.sang.topic.common.constants.MessageConstants;
import com.sang.topic.common.entity.Comment;
import com.sang.topic.common.entity.Post;
import com.sang.topic.common.entity.User;
import com.sang.topic.common.exception.ResultException;
import com.sang.topic.common.model.Page;
import com.sang.topic.repository.CommentRepository;
import com.sang.topic.service.CommentService;
import com.sang.topic.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    PostService postService;
    @Autowired
    CommentRepository commentRepository;

    @Override
    public List<Comment> getByPostIdAndPage(String postId, Page page) {
        org.springframework.data.domain.Page<Comment> p = commentRepository.findByPostId(postId, page.toPageable());
        page.setCount(p.getTotalElements());
        return p.getContent();
    }

    /**
        @author : liujx
        @description : 评论/回复
        @date : Create in 上午10:15 2018/1/8

    **/
    @Transactional
    @Override
    public Comment add(String content, String postId,String commentId, User user) throws ResultException {
        //查询对应的回复信息
        Comment commentRe = new Comment();
        if(!StringUtils.isEmpty(commentId)){
            commentRe  = commentRepository.findOne(commentId);
        }

        //查询对应的发帖信息
       Post post = postService.get(postId);
        if(post == null){
            throw  new ResultException(MessageConstants.POST_NOT_FOUND);
        }
        post.setCommentNumber(post.getCommentNumber() + 1);
        post.setUpdateTime(new Date());
        if (user == null)
            throw new ResultException(MessageConstants.USER_LOGIN_REQUIRE);
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setUserId(user.getId());
        comment.setUsername(user.getUsername());
        comment.setPostId(postId);
        comment.setAvailable(1);
        comment.setFloor(post.getCommentNumber());
        comment.setCreateTime(new Date());
        //如果是回复而不是评论,则添加对应的上级信息
        if(commentRe != null && !StringUtils.isEmpty(commentRe.getId())){
            comment.setParent(commentRe.getId());
            comment.setParentUserId(commentRe.getUserId());
            comment.setParentUserName(commentRe.getUsername());
        }
        postService.save(post);
        return commentRepository.save(comment);
    }
}
