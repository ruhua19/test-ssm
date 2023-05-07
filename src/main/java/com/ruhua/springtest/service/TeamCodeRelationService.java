package com.ruhua.springtest.service;

import com.ruhua.springtest.domain.CodeInfo;
import com.ruhua.springtest.domain.TeamCodeRelation;

import java.util.List;


/**
* @author 15968
* @description 针对表【team_code_relation】的数据库操作Service
* @createDate 2023-04-08 16:35:38
*/
public interface TeamCodeRelationService  {

    List<CodeInfo> getCodeByTeamId(Integer teamId);

    void addCode(Integer codeId,Integer userId);

    void removeCode(Integer codeId,Integer userId);

}
