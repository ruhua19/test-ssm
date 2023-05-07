package com.ruhua.springtest.service;

import com.ruhua.springtest.domain.TestResult;
import com.ruhua.springtest.param.TestResultParam;

import java.util.List;


/**
* @author 15968
* @description 针对表【test_result】的数据库操作Service
* @createDate 2023-04-08 16:35:38
*/
public interface TestResultService  {

    void addResult(TestResultParam testResultParam);

    TestResult getResultByCodeIdAndTestUser(Integer codeId, Integer testUser);

    List<TestResult> getResultByTestUser(Integer testUser);

    void deleteCodeByCodeIdAndTestUser(Integer codeId, Integer testUser);
}
