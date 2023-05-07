package com.ruhua.springtest.service.impl;


import com.ruhua.springtest.domain.TeamInfo;
import com.ruhua.springtest.domain.UserInfo;
import com.ruhua.springtest.mapper.UserInfoMapper;
import com.ruhua.springtest.param.TeamInfoParam;
import com.ruhua.springtest.service.TeamInfoService;
import com.ruhua.springtest.mapper.TeamInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
* @author 15968
* @description 针对表【team_info】的数据库操作Service实现
* @createDate 2023-04-08 16:35:38
*/
@Service
public class TeamInfoServiceImpl implements TeamInfoService{

    @Autowired
    TeamInfoMapper teamInfoMapper;

    @Autowired
    UserInfoMapper userInfoMapper;
    @Override
    public TeamInfo getTeamInfoByName(String name, Integer owner) {
       return teamInfoMapper.getTeamInfoByName(name, owner);
    }

    @Override
    public void createTeam(TeamInfoParam teamInfoParam) {
        teamInfoMapper.insertSelective(teamInfoFromParam(teamInfoParam));
    }

    @Override
    public void removeTeam(Integer id) {
        teamInfoMapper.deleteByPrimaryKey(id);
    }

    @Override
    public TeamInfo getTeamByUser(Integer userId) {
        UserInfo userInfoById = userInfoMapper.getUserInfoById(userId);
        Integer teamId = userInfoById.getTeamId();
        if(teamId==null){
            return null;
        }else {
            return teamInfoMapper.selectTeam(teamId);
        }

    }

    TeamInfo teamInfoFromParam(TeamInfoParam teamInfoParam){
        TeamInfo teamInfo = new TeamInfo();
        teamInfo.setName(teamInfoParam.getName());
        teamInfo.setOwner(teamInfoParam.getOwner());
        teamInfo.setCreateAt(new Date());
        teamInfo.setUpdateAt(new Date());
        return teamInfo;
    }
}




