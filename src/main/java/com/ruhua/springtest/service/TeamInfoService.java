package com.ruhua.springtest.service;

import com.ruhua.springtest.domain.TeamInfo;
import com.ruhua.springtest.param.TeamInfoParam;


/**
* @author 15968
* @description 针对表【team_info】的数据库操作Service
* @createDate 2023-04-08 16:35:38
*/
public interface TeamInfoService  {

    TeamInfo getTeamInfoByName(String name, Integer owner);

    void createTeam(TeamInfoParam teamInfoParam);

    void removeTeam(Integer id);

    TeamInfo getTeamByUser(Integer userId);
}
