package com.ruhua.springtest.mapper;

import com.ruhua.springtest.domain.CodeInfo;

import com.ruhua.springtest.param.CodeInfoParam;
import com.ruhua.springtest.vo.CodeInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.Date;
import java.util.List;

/**
* @author 15968
* @description 针对表【code_info】的数据库操作Mapper
* @createDate 2023-04-08 16:35:38
* @Entity com.ruhua.springtest.domain.CodeInfo
*/
@Mapper
public interface CodeInfoMapper   {

    CodeInfo checkTitleExist(@Param("title")String title, @Param("createUser")Integer createUser);

    void addCode(@Param("title")String title, @Param("createUser")Integer createUser, @Param("content")String content,
                 @Param("createAt") Date createAt, @Param("updateAt")Date updateAt);

    void updateCode(@Param("id")Integer id,@Param("title")String title, @Param("createUser")Integer createUser, @Param("content")String content, @Param("updateAt")Date updateAt);

    void deleteCodeByTitleAndCreateUser(@Param("title")String title, @Param("createUser")int createUser);

    List<CodeInfo> viewCodeByUser(@Param("createUser")String createUser);

    List<CodeInfo> getCodeByTeamId(@Param("teamId")Integer teamId);

    CodeInfo getCodeByCodeAndCreateUser(@Param("id") Integer code,@Param("createUser") Integer createUser);

    CodeInfo getCodeByCode(@Param("id") Integer code);

    List<CodeInfo> searchForCode(@Param("text")String text);
}




