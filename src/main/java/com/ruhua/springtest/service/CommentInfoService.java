package com.ruhua.springtest.service;

import com.ruhua.springtest.domain.CommentInfo;

import java.util.List;


/**
* @author 15968
* @description 针对表【comment_info】的数据库操作Service
* @createDate 2023-04-08 16:35:38
*/
public interface CommentInfoService  {

    void addComment(CommentInfo commentInfo);

    void removeComment(Integer commentId);

    List<CommentInfo> search(String text);

    List<CommentInfo> getCommentPage(Integer id);

    List<CommentInfo> getCommentsForCode(String codeId);
}
