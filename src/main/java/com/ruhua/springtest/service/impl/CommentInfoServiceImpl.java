package com.ruhua.springtest.service.impl;


import com.ruhua.springtest.domain.CommentInfo;
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
      return   commentInfoMapper.search(text);
    }

    @Override
    public List<CommentInfo> getCommentPage(Integer id) {
        return commentInfoMapper.getCommentPage(id);
    }

    @Override
    public List<CommentInfo> getCommentsForCode(String codeId) {
       return commentInfoMapper.getCommentsForCode(codeId);
    }
}




