package com.ruhua.springtest.service.impl;


import com.ruhua.springtest.domain.UserInfo;
import com.ruhua.springtest.param.UserInfoParam;
import com.ruhua.springtest.service.UserInfoService;
import com.ruhua.springtest.mapper.UserInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author 15968
 * @description 针对表【user_info】的数据库操作Service实现
 * @createDate 2023-04-08 16:35:38
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {


    @Autowired
    UserInfoMapper userInfoMapper;

    @Override
    public UserInfo getUserById(Integer userId) {
        return userInfoMapper.getUserInfoById(userId);
    }

    @Override
    public UserInfo getUserInfoById(Integer userId) {
        return userInfoMapper.getUserInfoById(userId);
    }

    @Override
    public UserInfo getUserInfoByUsernameAndPassword(String username, String password) {
        return userInfoMapper.getUserInfoByUsernameAndPassword(username, password);
    }

    @Override
    public void changePassword(Integer userId, String newPassword) {
        userInfoMapper.changePassword(userId, newPassword);
    }

    @Override
    public void register(UserInfoParam userInfoParam) {
        userInfoMapper.addUser(userInfoParam.getUsername(), userInfoParam.getPassword(),
                userInfoParam.getEmail(), new Date(), new Date());
    }

    @Override
    public void updateUserInfo(Integer userId, UserInfoParam userInfoParam) {
        userInfoMapper.updateUserInfo(userId,  userInfoParam.getUsername());
    }

    @Override
    public List<UserInfo> searchForUser(String text) {

      return  userInfoMapper.getUserInfo(text);
    }

    @Override
    public void removeTeamIdByTeamId(Integer id) {
        userInfoMapper.updateTeamId(id);

    }

    @Override
    public void updateTeamFromUser(Integer id, Integer owner) {
        userInfoMapper.updateTeamIdFromUser(id, owner);
    }

    @Override
    public List<UserInfo> getUserInfoByTeamId(Integer teamInfoId) {
       return userInfoMapper.getUserInfoByTeamId(teamInfoId);
    }
}




