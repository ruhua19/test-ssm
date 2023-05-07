package com.ruhua.springtest.service;

import com.ruhua.springtest.domain.CodeInfo;
import com.ruhua.springtest.param.CodeInfoParam;
import com.ruhua.springtest.vo.CodeInfoVO;

import java.util.List;


/**
* @author 15968
* @description 针对表【code_info】的数据库操作Service
* @createDate 2023-04-08 16:35:38
*/
public interface CodeInfoService  {

    boolean checkTitleExist(String title,Integer createUser);

    void addCode(CodeInfoParam codeInfoParam);

    CodeInfoVO viewCodeByTitleAndUser(String title, String createUser);

    CodeInfoVO getCodeByTitleAndCreateUser(String title, Integer createUser);
    CodeInfo getCodeByCodeAndCreateUser(Integer code, Integer createUser);

    void updateCode(CodeInfoParam codeInfoParam);

    void deleteCodeByTitleAndCreateUser(String title, String createUser);

    List<CodeInfo> viewCodeByUser(String createUser);

    List<CodeInfo> getCodeByTeamId(Integer teamId);
}
