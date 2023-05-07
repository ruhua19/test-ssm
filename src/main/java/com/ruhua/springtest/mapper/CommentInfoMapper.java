package com.ruhua.springtest.mapper;

import com.ruhua.springtest.domain.CommentInfo;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.Date;
import java.util.List;

/**
* @author 15968
* @description 针对表【comment_info】的数据库操作Mapper
* @createDate 2023-04-08 16:35:38
* @Entity com.ruhua.springtest.domain.CommentInfo
*/
@Mapper
public interface CommentInfoMapper extends BaseMapper<CommentInfo> {


    List<CommentInfo> search(@Param("text") String text);

    List<CommentInfo> getCommentPage(@Param("id")Integer id);

    List<CommentInfo> getCommentsForCode(@Param("codeId")String codeId);

    void delComment(@Param("id")Integer commentId);

    void insertComment(@Param("content")String content,@Param("create_user") Integer create_user,@Param("code_id") Integer code_id,
                       @Param("create_at")Date create_at,@Param("update_at") Date update_at);
}




