package com.ruhua.springtest.service.impl;


import com.ruhua.springtest.domain.CodeInfo;
import com.ruhua.springtest.param.CodeInfoParam;
import com.ruhua.springtest.service.CodeInfoService;
import com.ruhua.springtest.mapper.CodeInfoMapper;
import com.ruhua.springtest.vo.CodeInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author 15968
 * @description 针对表【code_info】的数据库操作Service实现
 * @createDate 2023-04-08 16:35:38
 */
@Service
public class CodeInfoServiceImpl implements CodeInfoService {
    @Autowired
    CodeInfoMapper codeInfoMapper;

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
        return codeInfoVO;

    }

    @Override
    public CodeInfoVO getCodeByTitleAndCreateUser(String title, Integer createUser) {
        CodeInfo codeInfo = codeInfoMapper.checkTitleExist(title, createUser);
        CodeInfoVO codeInfoVO = InfoToVO(codeInfo);
        return codeInfoVO;

    }

    @Override
    public CodeInfo getCodeByCodeAndCreateUser(Integer code, Integer createUser) {
      return   codeInfoMapper.getCodeByCodeAndCreateUser(code,createUser);
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
        return codeInfoMapper.viewCodeByUser(createUser);
    }

    @Override
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




