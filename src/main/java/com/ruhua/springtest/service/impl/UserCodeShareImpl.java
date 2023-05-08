package com.ruhua.springtest.service.impl;

import com.ruhua.springtest.domain.CodeInfo;
import com.ruhua.springtest.domain.UserCodeShareInfo;
import com.ruhua.springtest.domain.UserInfo;
import com.ruhua.springtest.mapper.CodeInfoMapper;
import com.ruhua.springtest.mapper.UserCodeShareMapper;
import com.ruhua.springtest.mapper.UserInfoMapper;
import com.ruhua.springtest.service.UserCodeShareService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserCodeShareImpl implements UserCodeShareService {

    @Autowired
    UserInfoMapper userInfoMapper;

    @Autowired
    UserCodeShareMapper userCodeShareMapper;

    @Autowired
    CodeInfoMapper codeInfoMapper;

    @Override
    public List<CodeInfo> getCode() {
        List<UserCodeShareInfo> code = userCodeShareMapper.getCode();
        List<CodeInfo> codeInfos = new ArrayList<>();
        code.forEach(o1->{
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
        userCodeShareMapper.addCode(codeId,userId,new Date(),new Date(),1);
    }

    @Override
    public void removeCode(Integer codeId, Integer userId) {
        userCodeShareMapper.removeCode(codeId);
    }
}
