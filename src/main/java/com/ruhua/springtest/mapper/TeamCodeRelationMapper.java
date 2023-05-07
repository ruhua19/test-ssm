package com.ruhua.springtest.mapper;

import com.ruhua.springtest.domain.TeamCodeRelation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.Date;
import java.util.List;


/**
* @author 15968
* @description 针对表【team_code_relation】的数据库操作Mapper
* @createDate 2023-04-08 16:35:38
* @Entity com.ruhua.springtest.domain.TeamCodeRelation
*/
@Mapper
public interface TeamCodeRelationMapper  {

   List<TeamCodeRelation> getCodeByTeamId(@Param("teamId") Integer teamId);

    void addCode(@Param("codeId") Integer codeId,@Param("teamId")  Integer teamId, @Param("createAt") Date createAt) ;

    void removeCode(@Param("codeId") Integer codeId, @Param("teamId") Integer teamId) ;
}




