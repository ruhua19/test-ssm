package com.ruhua.springtest.mapper;

import com.ruhua.springtest.domain.UserInfo;
import com.ruhua.springtest.param.UserInfoParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.Date;
import java.util.List;


/**
 * @author 15968
 * @description 针对表【user_info】的数据库操作Mapper
 * @createDate 2023-04-08 16:35:38
 * @Entity com.ruhua.springtest.domain.UserInfo
 */
@Mapper
public interface UserInfoMapper  {

    UserInfo getUserInfoById(@Param("userId") Integer userId);

    UserInfo getUserInfoByUsernameAndPassword(@Param("username") String username,
                                              @Param("password") String password);

    void changePassword(@Param("userId") Integer userId,
                        @Param("password") String newPassword);


    void addUser(@Param("username") String username, @Param("password") String password,
                 @Param("email")String email, @Param("createTime") Date createTime,
                 @Param("modifyTime") Date modifyTime);

    void updateUserInfo(@Param("userId")Integer userId,  @Param("name")String name);

    List<UserInfo> getUserInfo(@Param("username")String text);

    void updateTeamId(@Param("id")Integer id);

    void updateTeamIdFromUser(@Param("id")Integer id, @Param("owner")Integer owner);

    List<UserInfo> getUserInfoByTeamId(@Param("teamId") Integer teamInfoId);
}




