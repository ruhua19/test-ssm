package com.ruhua.springtest.mapper;

import com.ruhua.springtest.domain.TeamInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.BaseMapper;


/**
* @author 15968
* @description 针对表【team_info】的数据库操作Mapper
* @createDate 2023-04-08 16:35:38
* @Entity com.ruhua.springtest.domain.TeamInfo
*/
@Mapper
public interface TeamInfoMapper extends BaseMapper<TeamInfo> {


    TeamInfo getTeamInfoByName(@Param("name")String name, @Param("owner")Integer owner);

    TeamInfo selectTeam(@Param("teamId") Integer teamId);
}




