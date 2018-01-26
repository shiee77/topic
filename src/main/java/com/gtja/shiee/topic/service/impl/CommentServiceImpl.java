package com.gtja.shiee.topic.service.impl;


import com.gtja.shiee.topic.common.constants.MessageConstants;
import com.gtja.shiee.topic.common.entity.Comment;
import com.gtja.shiee.topic.common.entity.User;
import com.gtja.shiee.topic.common.exception.ResultException;
import com.gtja.shiee.topic.common.model.Page;
import com.gtja.shiee.topic.repository.CommentRepository;
import com.gtja.shiee.topic.service.CommentService;
import com.gtja.shiee.topic.service.PostService;
import com.gtja.shiee.topic.common.entity.Post;
import com.gtja.shiee.topic.util.SensitiveFilter;
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
        //过滤敏感词
        // 使用默认单例（加载默认词典）
        SensitiveFilter filter = SensitiveFilter.DEFAULT;
        // 进行过滤
        content = filter.filter(content, '*');

        //查询对应的回复信息
        Comment commentRe = new Comment();
        if(!StringUtils.isEmpty(commentId)){
            commentRe  = commentRepository.findOne(commentId);
        }

        //查询对应的发帖信息
       Post post = postService.get(postId,null, false);
        if(post == null){
            throw  new ResultException(MessageConstants.POST_NOT_FOUND);
        }
        //增加帖子的回复数量
        post.setCommentNumber(post.getCommentNumber() + 1);
        post.setUpdateTime(new Date());
        if (user == null)
            throw new ResultException(MessageConstants.USER_LOGIN_REQUIRE);
        Comment comment = new Comment();
        //过滤掉的敏感词插入
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
        //保存发帖信息
        postService.save(post);
        //保存回复信息
        return commentRepository.save(comment);
    }
    
    /**
        @author : liujx
        @description : 删除评论
        @date : Create in 上午9:47 2018/1/10
    
    **/
    @Transactional
    @Override
    public Comment del(String commentId, User user) throws ResultException {
        //查询对应的回复信息
        Comment commentRe = new Comment();
        if(!StringUtils.isEmpty(commentId)){
            commentRe  = commentRepository.findOne(commentId);
        }

        //未找到对应的回复信息
        if(StringUtils.isEmpty(commentId) || commentRe == null){
            throw  new ResultException(MessageConstants.COMMENT_NOT_FOUND);
        }
        //如果用户信息为空或者不是该用户的评论回复
        if (user == null || !user.getId().equals(commentRe.getUserId()))
            throw new ResultException(MessageConstants.USER_LOGIN_REQUIRE);
        //如果找不到此帖子
        Post post =  postService.get(commentRe.getPostId(),null, false);
        if(post == null){
            throw new ResultException(MessageConstants.POST_NOT_FOUND);
        }
        //帖子回复数量减1
        post.setCommentNumber(StringUtils.isEmpty(post.getCommentNumber()) ? 0 : (post.getCommentNumber() - 1));
        //更新帖子回复信息
        postService.save(post);
        //删除评论
        commentRepository.delete(commentRe.getId());

        return commentRe;
    }
}
