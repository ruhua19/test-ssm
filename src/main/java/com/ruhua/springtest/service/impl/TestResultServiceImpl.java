package com.ruhua.springtest.service.impl;


import com.ruhua.springtest.domain.TestResult;
import com.ruhua.springtest.param.TestResultParam;
import com.ruhua.springtest.service.TestResultService;
import com.ruhua.springtest.mapper.TestResultMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
* @author 15968
* @description 针对表【test_result】的数据库操作Service实现
* @createDate 2023-04-08 16:35:38
*/
@Service
public class TestResultServiceImpl
    implements TestResultService{
    @Autowired
    TestResultMapper testResultMapper;

    @Override
    public void addResult(TestResultParam testResultParam) {
        testResultMapper.insertSelective(infoFromParam(testResultParam));
    }

    @Override
    public TestResult getResultByCodeIdAndTestUser(Integer codeId, Integer testUser) {
       return testResultMapper.selectByPrimaryKey(codeId);
    }

    @Override
    public List<TestResult> getResultByTestUser(Integer testUser) {
       return testResultMapper.search(testUser);
    }

    @Override
    public void deleteCodeByCodeIdAndTestUser(Integer codeId, Integer testUser) {
        testResultMapper.deleteByPrimaryKey(codeId);

    }
    TestResult infoFromParam(TestResultParam testResultParam){
        TestResult testResult = new TestResult();
        testResult.setCodeId(testResultParam.getCodeId());
        testResult.setTestUser(testResultParam.getTestUser());
        testResult.setCreateAt(new Date());
        testResult.setUpdateAt(new Date());
        testResult.setTestTime(new Date());
        testResult.setResult(testResultParam.getResult());
        return testResult;
    }
}



