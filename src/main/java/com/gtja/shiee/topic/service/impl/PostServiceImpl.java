package com.gtja.shiee.topic.service.impl;


import com.gtja.shiee.topic.common.constants.ResultConstants;
import com.gtja.shiee.topic.common.entity.User;
import com.gtja.shiee.topic.common.entity.UserLiked;
import com.gtja.shiee.topic.common.model.Page;
import com.gtja.shiee.topic.repository.CommentRepository;
import com.gtja.shiee.topic.repository.PostRepository;
import com.gtja.shiee.topic.repository.TopicRepository;
import com.gtja.shiee.topic.repository.UserLikedRepository;
import com.gtja.shiee.topic.service.PostService;
import com.gtja.shiee.topic.common.constants.CommonConstants;
import com.gtja.shiee.topic.common.constants.MessageConstants;
import com.gtja.shiee.topic.common.entity.Post;
import com.gtja.shiee.topic.common.entity.Topic;
import com.gtja.shiee.topic.common.exception.ResultException;
import com.gtja.shiee.topic.util.SensitiveFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    PostRepository postRepository;
    @Autowired
    TopicRepository topicRepository;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    UserLikedRepository userLikedRepository;

    @Override
    public List<Post> getAll(Page page) {
        org.springframework.data.domain.Page<Post> p = postRepository.findAll(page.toPageable());
        page.setCount(p.getTotalElements());
        return p.getContent();
    }

    @Override
    public List<Post> getByTopicId(String topicId, Page page) {
        Sort sort = null;
        Topic topic = topicRepository.findOne(topicId);
        if(topic.getOrderType() == CommonConstants.OrderType.CREATE_TIME_FIRST) {
            sort = new Sort(Sort.Direction.DESC, "createTime");
        }else if(topic.getOrderType() == CommonConstants.OrderType.UPDATE_TIME_FIRST){
            sort = new Sort(Sort.Direction.DESC, "updateTime");
        }
        org.springframework.data.domain.Page<Post> p = postRepository.findByTopicId(topicId, page.toPageable(sort));
        page.setCount(p.getTotalElements());
        return p.getContent();
    }

    /**
        @author : liujx
        @description : 获取帖子信息
        id 帖子id, user 用户信息, flag 是否增加浏览记录
        @date : Create in 上午9:47 2018/1/15
    
    **/
    @Override
    public Post get(String id,User user, boolean flag) throws ResultException {
        Post post = postRepository.findOne(id);
        if (post == null)
            throw new ResultException(MessageConstants.POST_NOT_FOUND, ResultConstants.NOT_FOUND);
        if(flag){
            //记录帖子的浏览记录
            if(StringUtils.isEmpty(post.getReadCount())) {
                post.setReadCount(1L);
            }else{
                post.setReadCount(post.getReadCount()+1);
            }
            postRepository.save(post);
            if(user != null){
                //如果用户信息不为空,则添加对应的浏览记录到用户浏览表
                List<UserLiked>  userLikeds = userLikedRepository.findByUserIdAndPostId(user.getId(), id);
                if(userLikeds != null && userLikeds.size() > 0){
                    userLikeds.get(0).setScan(true);
                    userLikedRepository.save(userLikeds);
                }else{
                    UserLiked userLiked = new UserLiked();
                    userLiked.setUserId(user.getId());
                    userLiked.setPostId(id);
                    userLiked.setScan(true);
                    userLikedRepository.save(userLiked);

                }
            }
        }

        return post;
    }

    @Transactional
    @Override
    public Post add(String title, String content, String topicId, User user) throws ResultException {
        if (user == null)
            throw new ResultException(MessageConstants.USER_LOGIN_REQUIRE);
        //过滤敏感词
        // 使用默认单例（加载默认词典）
        SensitiveFilter filter = SensitiveFilter.DEFAULT;
        // 进行过滤
        content = filter.filter(content, '*');
        title = filter.filter(title, '*');

        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);
        post.setTopicId(topicId);
        post.setAvailable(1);
        post.setCommentNumber(0);
        post.setUserId(user.getId());
        post.setUsername(user.getUsername());
        post.setCreateTime(new Date());
        post.setUpdateTime(new Date());
        postRepository.save(post);
        return post;
    }

    @Transactional
    @Override
    public Post save(Post post) {
        return postRepository.save(post);
    }


    /**
        @author : liujx
        @description : 删除对应的帖子信息
        @date : Create in 下午1:46 2018/1/10
    
    **/
    @Transactional
    @Override
    public Post del( String postId, User user) throws ResultException {
        Post post = postRepository.findOne(postId);
        //如果未查询到对应的帖子信息
        if(post == null){
            throw new ResultException(MessageConstants.POST_NOT_FOUND);
        }
        //如果没有用户信息或者用户不是发帖人
        if (user == null || !user.getId().equals(post.getUserId()))
            throw new ResultException(MessageConstants.USER_LOGIN_REQUIRE);

        //删除该帖子下所有的回复
        commentRepository.deleteByPostId(postId);

        //删除该帖子
        postRepository.delete(post);

        return post;
    }


    /**
        @author : liujx
        @description : 获取当前用户是否点赞
        @date : Create in 下午3:09 2018/1/10
    
    **/
    @Override
    public Integer getIsUserLiked(String postId, User user) throws ResultException {
        //用户不能为空
        if(user == null) throw new ResultException(MessageConstants.USER_LOGIN_REQUIRE);
        //帖子信息不能查不到
        if(StringUtils.isEmpty(postId)) throw new ResultException(MessageConstants.POST_NOT_FOUND);
        Post post = postRepository.findOne(postId);
        if(post == null) throw new ResultException(MessageConstants.POST_NOT_FOUND);

        //根据帖子信息和用户信息查询是否有对应信息
        List<UserLiked> userLikeds = userLikedRepository.findByUserIdAndPostId(user.getId(),postId);
        return (userLikeds == null || userLikeds.size() <= 0 || !userLikeds.get(0).isLiked()) ? 0 : 1;
    }

    /**
        @author : liujx
        @description : 点赞帖子
        @date : Create in 下午2:57 2018/1/10
    
    **/
    @Transactional
    @Override
    public Post liked( String postId, User user) throws ResultException {
        Post post = postRepository.findOne(postId);
        //如果未查询到对应的帖子信息
        if(post == null){
            throw new ResultException(MessageConstants.POST_NOT_FOUND);
        }
        //如果没有用户信息
        if (user == null)
            throw new ResultException(MessageConstants.USER_LOGIN_REQUIRE);

        post.setLikedCount(StringUtils.isEmpty(post.getLikedCount()) ? 1 : (post.getLikedCount()+1) );
        List<UserLiked> userLikeds = userLikedRepository.findByUserIdAndPostId(user.getId(), postId);
        if(userLikeds != null){
            userLikeds.get(0).setLiked(true);
            userLikedRepository.save(userLikeds.get(0));
        }else{
            //新增点赞表
            UserLiked userLiked = new UserLiked();
            userLiked.setUserId(user.getId());
            userLiked.setPostId(postId);
            userLiked.setLiked(true);
            userLikedRepository.save(userLiked);
        }


        //修改点赞数量
        postRepository.save(post);

        return post;
    }
    
    /**
        @author : liujx
        @description : 取消点赞
        @date : Create in 下午3:47 2018/1/10
    
    **/
    @Transactional
    @Override
    public Post cancelLiked( String postId, User user) throws ResultException {
        Post post = postRepository.findOne(postId);
        //如果未查询到对应的帖子信息
        if(post == null){
            throw new ResultException(MessageConstants.POST_NOT_FOUND);
        }
        //如果没有用户信息
        if (user == null)
            throw new ResultException(MessageConstants.USER_LOGIN_REQUIRE);

        post.setLikedCount(StringUtils.isEmpty(post.getLikedCount()) ? 0 : (post.getLikedCount()-1) );

        List<UserLiked> userLikeds = userLikedRepository.findByUserIdAndPostId(user.getId(),postId);

        //删除点赞表数据
        userLikeds.forEach(userLiked -> {
            userLiked.setLiked(false);
            userLikedRepository.save(userLiked);
        });


        //修改点赞数量
        postRepository.save(post);

        return post;
    }


    /**
        @author : liujx
        @description : 获取所有对应帖子id的帖子信息
        @date : Create in 上午10:56 2018/1/15
    
    **/
    @Override
    public List<Post> getPostByIds(List<String> ids) {
        return postRepository.findByIdIn(ids);
    }

    /**
        @author : liujx
        @description : 获取所有的帖子信息
        @date : Create in 下午1:39 2018/1/15
    
    **/
    @Override
    public List<Post> getTopicAll(Page page) {
        org.springframework.data.domain.Page<Post> p =  postRepository.findAll(page.toPageable());
        page.setCount(p.getTotalElements());
        return p.getContent();
    }

    /**
        @author : liujx
        @description : 根据搜索条件检索对应的数据信息
        @date : Create in 下午2:35 2018/1/15
    
    **/
    @Override
    public List<Post> getTopicAllBySearchInfo(String searchInfo, Page page) {
        org.springframework.data.domain.Page<Post> p =  postRepository.findByTitleLike(searchInfo,page.toPageable());
        page.setCount(p.getTotalElements());
        return p.getContent();
    }

    /**
        @author : liujx
        @description : 根据搜索条件和topicId检索对应的数据信息
        @date : Create in 下午2:41 2018/1/15
    
    **/
    @Override
    public List<Post> getByTopicIdAndSearchInfo(String topicId, String searchInfo, Page page) {
        org.springframework.data.domain.Page<Post> p =  postRepository.findByTopicIdAndTitleLike(topicId, searchInfo,page.toPageable());
        page.setCount(p.getTotalElements());
        return p.getContent();
    }
}
