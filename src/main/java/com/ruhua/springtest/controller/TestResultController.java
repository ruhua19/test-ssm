package com.ruhua.springtest.controller;

import com.ruhua.springtest.domain.UserInfo;
import com.ruhua.springtest.param.TestResultParam;
import com.ruhua.springtest.service.TestResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class TestResultController {

    @Autowired
    TestResultService testResultService;

    // 查看测试结果
    @PostMapping("/getResult")
    String getResult(@RequestParam("codeId") Integer codeId, @RequestParam("testUser") Integer testUser, Model model) {
        // 判断 codeId 和 testUser 不为空
        if (codeId == null || testUser == null) {
            model.addAttribute("error", "codeId 或 testUser 为空");
            return "resultInfoList";
        }
        // 如果为空 将错误信息放入到model 之中 并且返回到错误界面
        // 查询数据库返回结果
        // 使用testResultService中的getResultByCodeIdAndTestUser方法查询数据库，将结果放入model中，返回结果页面
        model.addAttribute("result", testResultService.getResultByCodeIdAndTestUser(codeId, testUser));
        return "resultInfo";
    }

    // 查看所有的测试结果
    @GetMapping("/getResultList")
    String getResultList( Model model, HttpSession session) {
        UserInfo userInfo =(UserInfo) session.getAttribute("userInfo");
        Integer testUser =    userInfo.getId();
        //  testUser 不为空
        if (testUser == null) {
            model.addAttribute("error", "testUser 为空");
            return "index";
        }
        // 如果为空 将错误信息放入到model 之中 并且返回到错误界面
        // 查询数据库返回结果
        model.addAttribute("results", testResultService.getResultByTestUser(testUser));
        return "resultInfoList";
    }
    @PostMapping("/removeResult")
    String removeResult(@RequestParam("codeId")Integer codeId,@RequestParam("testUser") Integer testUser, Model model) {
        // 判断 codeId 和 testUser 不为空
        if (codeId == null || testUser == null) {
            model.addAttribute("error", "codeId 或 testUser 为空");
            return "resultInfoList ";
        }
        // 如果为空 将错误信息放入到model 之中 并且返回到错误界面
        // 删除数据库中 条件为codeId 和 testUser
        testResultService.deleteCodeByCodeIdAndTestUser(codeId, testUser);
        return "resultInfoList";
    }

    @PostMapping("/addResult")
    String addResult(Model model,@RequestParam Map<String,Object>  testResult) {
        TestResultParam testResultParam = mapToTestResultParam(testResult);
        // 判断 codeId 和 testUser 不为空
        if (testResultParam.getCodeId() == null || testResultParam.getTestUser() == null) {
            model.addAttribute("error", "codeId 或 testUser 为空");
            return "error";
        }
        // 如果为空 将错误信息放入到model 之中 并且返回到错误界面
        testResultService.addResult(testResultParam);
        // 放入数据库
        return "codeInfoList";
    }



TestResultParam mapToTestResultParam(@RequestBody Map<String, Object> map) {
    TestResultParam testResultParam = new TestResultParam();
    // 从map中取出值放到testResultParam之中
    testResultParam.setCodeId((Integer) map.get("codeId"));
    testResultParam.setTestUser((Integer) map.get("testUser"));
    testResultParam.setResult((String) map.get("result"));
    return testResultParam;
}


}

