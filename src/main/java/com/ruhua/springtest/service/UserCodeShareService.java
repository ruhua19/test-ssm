package com.ruhua.springtest.service;

import com.ruhua.springtest.domain.CodeInfo;

import java.util.List;

public interface UserCodeShareService {

    List<CodeInfo> getCode();

    void addCode(Integer codeId,Integer userId);

    void removeCode(Integer codeId,Integer userId);
}
