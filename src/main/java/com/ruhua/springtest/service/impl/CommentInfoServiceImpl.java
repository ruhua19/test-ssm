package com.ruhua.springtest.service.impl;


import com.ruhua.springtest.domain.CommentInfo;
import com.ruhua.springtest.domain.UserInfo;
import com.ruhua.springtest.mapper.UserInfoMapper;
import com.ruhua.springtest.service.CommentInfoService;
import com.ruhua.springtest.mapper.CommentInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
* @author 15968
* @description 针对表【comment_info】的数据库操作Service实现
* @createDate 2023-04-08 16:35:38
*/
@Service
public class CommentInfoServiceImpl implements CommentInfoService{

    @Autowired
    CommentInfoMapper commentInfoMapper;

    @Autowired
    UserInfoMapper userInfoMapper;
    @Override
    public void addComment(CommentInfo commentInfo) {
        commentInfoMapper.insertComment(commentInfo.getContent(),commentInfo.getCreateUser(),commentInfo.getCodeId(),new Date(),new Date());
    }

    @Override
    public void removeComment(Integer commentId) {
        commentInfoMapper.delComment(commentId);
    }

    @Override
    public List<CommentInfo> search(String text) {
        List<CommentInfo> search = commentInfoMapper.search(text);
        search.forEach(o1->{
            UserInfo userInfoById = userInfoMapper.getUserInfoById(o1.getCreateUser());
            o1.setUserName(userInfoById.getUsername());
        });
        return search;
    }

    @Override
    public List<CommentInfo> getCommentPage(Integer id) {
        List<CommentInfo> commentPage = commentInfoMapper.getCommentPage(id);
        commentPage.forEach(o1->{
            UserInfo userInfoById = userInfoMapper.getUserInfoById(o1.getCreateUser());
            o1.setUserName(userInfoById.getUsername());
        });
        return commentPage;
    }

    @Override
    public List<CommentInfo> getCommentsForCode(String codeId) {
        List<CommentInfo> commentsForCode = commentInfoMapper.getCommentsForCode(codeId);
        commentsForCode.forEach(o1->{
            UserInfo userInfoById = userInfoMapper.getUserInfoById(o1.getCreateUser());
            o1.setUserName(userInfoById.getUsername());
        });
        return commentsForCode;
    }
}




