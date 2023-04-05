package com.ruhua.springtest.service;

import com.ruhua.springtest.entity.User;
import com.ruhua.springtest.mapper.UserMapper;
import com.ruhua.springtest.param.UserParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;

    //基于SSM的JAVA在线单元编码与测试平台的设计与实现
    public boolean getUser(UserParam userParam) {

        User userFromId = userMapper.getUserFromId(userParam.getId());
        if (userFromId == null) {
            return false;
        }
        if (userFromId.getName().equals(userParam.getName())) {
            if (userFromId.getPassword().equals(userParam.getPassword())) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
