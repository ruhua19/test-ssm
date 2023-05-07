package com.ruhua.springtest.mapper;

import com.ruhua.springtest.domain.TestResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;


/**
* @author 15968
* @description 针对表【test_result】的数据库操作Mapper
* @createDate 2023-04-08 16:35:38
* @Entity com.ruhua.springtest.domain.TestResult
*/
@Mapper
public interface TestResultMapper extends BaseMapper<TestResult> {

    List<TestResult> search(@Param("testUser")Integer testUser);
}




