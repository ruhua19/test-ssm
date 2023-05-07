package com.ruhua.springtest.service;

import com.ruhua.springtest.domain.UserInfo;
import com.ruhua.springtest.param.UserInfoParam;

import java.util.List;


/**
* @author 15968
* @description 针对表【user_info】的数据库操作Service
* @createDate 2023-04-08 16:35:38
*/
public interface UserInfoService {

     UserInfo getUserById(Integer userId) ;

    UserInfo getUserInfoById(Integer userId);

    UserInfo getUserInfoByUsernameAndPassword(String username, String password);

    void changePassword(Integer userId,  String newPassword);

    void register(UserInfoParam userInfoParam);

    void updateUserInfo(Integer userId, UserInfoParam userInfoParam);

    List<UserInfo> getUserInfo(String text);



    void removeTeamIdByTeamId(Integer id);

    void updateTeamFromUser(Integer id, Integer owner);
}
