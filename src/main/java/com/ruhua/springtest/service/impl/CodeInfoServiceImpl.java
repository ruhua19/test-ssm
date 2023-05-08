package com.ruhua.springtest.service.impl;


import com.ruhua.springtest.domain.CodeInfo;
import com.ruhua.springtest.domain.UserInfo;
import com.ruhua.springtest.mapper.UserInfoMapper;
import com.ruhua.springtest.param.CodeInfoParam;
import com.ruhua.springtest.service.CodeInfoService;
import com.ruhua.springtest.mapper.CodeInfoMapper;
import com.ruhua.springtest.service.UserCodeShareService;
import com.ruhua.springtest.vo.CodeInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 15968
 * @description 针对表【code_info】的数据库操作Service实现
 * @createDate 2023-04-08 16:35:38
 */
@Service
public class CodeInfoServiceImpl implements CodeInfoService {
    @Autowired
    CodeInfoMapper codeInfoMapper;

    @Autowired
    UserInfoMapper userInfoMapper;

    @Autowired
    UserCodeShareService userCodeShareService;

    @Override
    public boolean checkTitleExist(String title, Integer createUser) {
        CodeInfo codeInfo = codeInfoMapper.checkTitleExist(title, createUser);
        if (codeInfo == null) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void addCode(CodeInfoParam codeInfoParam) {
        codeInfoMapper.addCode(codeInfoParam.getTitle(),codeInfoParam.getCreateUser(),codeInfoParam.getContent(),new Date(),new Date());
    }

    @Override
    public CodeInfoVO viewCodeByTitleAndUser(String title, String createUser) {
        CodeInfo codeInfo = codeInfoMapper.checkTitleExist(title, Integer.parseInt(createUser));
        CodeInfoVO codeInfoVO = InfoToVO(codeInfo);
        UserInfo userInfoById = userInfoMapper.getUserInfoById(codeInfoVO.getCreateUser());
        codeInfoVO.setUserName(userInfoById.getUsername());
        return codeInfoVO;

    }

    @Override
    public CodeInfoVO getCodeByTitleAndCreateUser(String title, Integer createUser) {
        CodeInfo codeInfo = codeInfoMapper.checkTitleExist(title, createUser);
        CodeInfoVO codeInfoVO = InfoToVO(codeInfo);
        UserInfo userInfoById = userInfoMapper.getUserInfoById(codeInfoVO.getCreateUser());
        codeInfoVO.setUserName(userInfoById.getUsername());
        return codeInfoVO;

    }

    @Override
    public CodeInfo getCodeByCodeAndCreateUser(Integer code, Integer createUser) {
        CodeInfo codeByCodeAndCreateUser = codeInfoMapper.getCodeByCodeAndCreateUser(code, createUser);

            UserInfo userInfoById = userInfoMapper.getUserInfoById(codeByCodeAndCreateUser.getCreateUser());
        codeByCodeAndCreateUser.setUserName(userInfoById.getUsername());

        return codeByCodeAndCreateUser;
    }

    @Override
    public void updateCode(CodeInfoParam codeInfoParam) {
        codeInfoMapper.updateCode(codeInfoParam.getId(),codeInfoParam.getTitle(),codeInfoParam.getCreateUser(),codeInfoParam.getContent(),new Date());
    }

    @Override
    public void deleteCodeByTitleAndCreateUser(String title, String createUser) {
        codeInfoMapper.deleteCodeByTitleAndCreateUser(title, Integer.parseInt(createUser));
    }

    @Override
    public List<CodeInfo> viewCodeByUser(String createUser) {
        List<CodeInfo> codeInfos = codeInfoMapper.viewCodeByUser(createUser);
        codeInfos.forEach(o1->{
            UserInfo userInfoById = userInfoMapper.getUserInfoById(o1.getCreateUser());
            o1.setUserName(userInfoById.getUsername());
        });
        return codeInfos;
    }

    @Override
    public List<CodeInfo> searchForCode(String text) {
        List<CodeInfo> code = userCodeShareService.getCode();
        List<CodeInfo> collect = code.stream().filter(codeInfo -> codeInfo.getTitle().contains(text)
                || codeInfo.getContent().contains(text)).collect(Collectors.toList());
        collect.forEach(o1->{
            UserInfo userInfoById = userInfoMapper.getUserInfoById(o1.getCreateUser());
            o1.setUserName(userInfoById.getUsername());
        });
        return collect;
    }


    public List<CodeInfo> getCodeByTeamId(Integer teamId) {
        return codeInfoMapper.getCodeByTeamId(teamId);
    }

    CodeInfoVO InfoToVO(CodeInfo codeInfo) {
        CodeInfoVO codeInfoVO = new CodeInfoVO();
        codeInfoVO.setId(codeInfo.getId());
        codeInfoVO.setTitle(codeInfo.getTitle());
        codeInfoVO.setContent(codeInfo.getContent());
        codeInfoVO.setCreateUser(codeInfo.getCreateUser());
        codeInfoVO.setCreateAt(codeInfo.getCreateAt());
        codeInfoVO.setUpdateAt(codeInfo.getUpdateAt());
        return codeInfoVO;

    }
}




