package com.ruhua.springtest.service.impl;


import com.ruhua.springtest.domain.CodeInfo;
import com.ruhua.springtest.domain.TeamCodeRelation;
import com.ruhua.springtest.domain.UserInfo;
import com.ruhua.springtest.mapper.CodeInfoMapper;
import com.ruhua.springtest.mapper.UserInfoMapper;
import com.ruhua.springtest.service.TeamCodeRelationService;
import com.ruhua.springtest.mapper.TeamCodeRelationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
* @author 15968
* @description 针对表【team_code_relation】的数据库操作Service实现
* @createDate 2023-04-08 16:35:38
*/
@Service
public class TeamCodeRelationServiceImpl implements TeamCodeRelationService{

  @Autowired
  TeamCodeRelationMapper teamCodeRelationMapper;

  @Autowired
    CodeInfoMapper codeInfoMapper;

  @Autowired
    UserInfoMapper userInfoMapper;

    @Override
    public List<CodeInfo> getCodeByTeamId(Integer teamId) {
        List<TeamCodeRelation> codeByTeamId = teamCodeRelationMapper.getCodeByTeamId(teamId);
        List<CodeInfo> codeInfos = new ArrayList<>();
        codeByTeamId.forEach(o1->{
            codeInfos.add( codeInfoMapper.getCodeByCode(o1.getCodeId()));
        });
        codeInfos.forEach(o1->{
            UserInfo userInfoById = userInfoMapper.getUserInfoById(o1.getCreateUser());
            o1.setUserName(userInfoById.getUsername());
        });
        return codeInfos;
    }

    @Override
    public void addCode(Integer codeId, Integer userId) {
        Integer teamId = userInfoMapper.getUserInfoById(userId).getTeamId();
        teamCodeRelationMapper.addCode(codeId,teamId,new Date());

    }

    @Override
    public void removeCode(Integer codeId, Integer userId) {
        Integer teamId = userInfoMapper.getUserInfoById(userId).getTeamId();
        teamCodeRelationMapper.removeCode(codeId,teamId);
    }
}




