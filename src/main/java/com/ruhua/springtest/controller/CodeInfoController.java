package com.ruhua.springtest.controller;


import com.ruhua.springtest.domain.CodeInfo;
import com.ruhua.springtest.domain.UserInfo;
import com.ruhua.springtest.param.CodeInfoParam;
import com.ruhua.springtest.service.CodeInfoService;

import com.ruhua.springtest.service.TestResultService;
import com.ruhua.springtest.vo.CodeInfoVO;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
public class CodeInfoController {

    @Autowired
    CodeInfoService codeInfoService;

    @Autowired
    TestResultService testResultService;

// 增加版本

    @PostMapping("/addCode")

    public String addCode(@RequestParam Map<String, Object> code, Model model,HttpSession session) {
        UserInfo userInfo = (UserInfo)session.getAttribute("userInfo");
        model.addAttribute("userInfo",userInfo);
        CodeInfoParam codeInfoParam = createCodeInfoParam(code);
        // 判读参数 createUser 和 content不能为空 和 title 不能为空
        // 插入到代码表里
        // 如果为空 将错误信息放入到model 之中 并且返回到错误界面
        // 增加时 判断title不重复
        try {
            if (StringUtils.isEmpty(codeInfoParam.getCreateUser()) || StringUtils.isEmpty(codeInfoParam.getContent())
                    || StringUtils.isEmpty(codeInfoParam.getTitle())) {
                model.addAttribute("error", "参数不能为空");
                return "createCode";
            }
            if (codeInfoService.checkTitleExist(codeInfoParam.getTitle(),codeInfoParam.getCreateUser())) {
                model.addAttribute("error", "标题已存在");
                return "createCode";
            }
            codeInfoService.addCode(codeInfoParam);
            model.addAttribute("error", "成功添加");
            return "createCode";
        } catch (Exception e) {
            System.out.println(e);
            model.addAttribute("error", "增加版本错误");
            return "createCode";
        }
    }


    /**
     * 根据标题和创建者查看代码
     *
     * @param title      标题
     * @param createUser 创建者
     * @param model      模型
     * @return 代码内容
     */
    @GetMapping("/getCode")
    public String viewCode(@RequestParam("title") String title, @RequestParam("createUser") String createUser,
                           Model model,HttpSession session) {
        // 判读参数 title 和 createUser 不能为空
        if (StringUtils.isEmpty(title) || StringUtils.isEmpty(createUser)) {
            model.addAttribute("error", "参数不能为空");
            return "codeInfoList";
        }
        UserInfo userInfo = (UserInfo)session.getAttribute("userInfo");
        model.addAttribute("userInfo",userInfo);
        // 从数据库里根据 title 和 createUser 去查询代码
        model.addAttribute("code", codeInfoService.viewCodeByTitleAndUser(title, createUser));
        return "codeInfo";
    }

    /**
     * 修改代码
     *
     *
     * @param model         模型
     * @return 修改结果
     */
    @PostMapping("/modifyCode")
    public String modifyCode(@RequestParam Map<String, Object> code, Model model) {
        CodeInfoParam codeInfoParam = createCodeInfoParam(code);
        // 判读参数 createUser 和 content不能为空 和 title 不能为空
        if (StringUtils.isEmpty(codeInfoParam.getCreateUser()) || StringUtils.isEmpty(codeInfoParam.getContent()) || StringUtils.isEmpty(codeInfoParam.getTitle())) {
            model.addAttribute("error", "参数不能为空");
            return "codeInfo";
        }
        // 先从数据里按照title和create_user查找代码
        CodeInfoVO codeInfo = codeInfoService.viewCodeByTitleAndUser(codeInfoParam.getTitle(), codeInfoParam.getCreateUser().toString());
        if (codeInfo == null) {
            model.addAttribute("error", "代码不存在");
            return "codeInfoList";
        }
        // 更新代码
        if (codeInfoService.checkTitleExist(codeInfoParam.getTitle(),codeInfoParam.getCreateUser())) {
            model.addAttribute("error", "标题已存在");
            return "codeInfo";
        }

        codeInfoService.updateCode(codeInfoParam);
        return "codeInfo";
    }

    /**
     * 删除代码
     *
     * @param title      标题
     * @param createUser 创建者
     * @param model      模型
     * @return 删除结果
     */
    @GetMapping("/delCode")
    public String searchCode(@RequestParam("title") String title, @RequestParam("createUser") String createUser, Model model) {
        // 判读参数 createUser  和 title 不能为空
        if (StringUtils.isEmpty(title) || StringUtils.isEmpty(createUser)) {
            model.addAttribute("error", "参数不能为空");
            return "codeInfoList";
        }
        // 删除title的代码
        codeInfoService.deleteCodeByTitleAndCreateUser(title, createUser);
        return "codeInfoList";
    }

    /**
     * 根据创建者查看代码
     *
     * @param model      模型
     * @return 代码内容
     */
    // 查看我的代码提交记录
    @GetMapping("/getCodeList")
    public String getCodeList( Model model,HttpSession session) {
        // createUser 不能为空
        UserInfo userInfo =(UserInfo) session.getAttribute("userInfo");
      String  createUser =    userInfo.getUsername();
        if (StringUtils.isEmpty(createUser)) {
            model.addAttribute("error", "参数不能为空");
            return "index";
        }
        // 从数据库里根据 title 和 createUser 去查询代码
        List<CodeInfo> codeInfos = codeInfoService.viewCodeByUser(createUser);
        model.addAttribute("codes", codeInfos);


        session.setAttribute("codeList",codeInfoService.viewCodeByUser(createUser));

        return "codeInfoList";
    }



//    public String getResult(String code) {
//        Integer id = codeInfoService.viewCodeByTitleAndUser(codeInfoParam.getTitle(),
//                codeInfoParam.getCreateUser().toString()).getId();
//        TestResultParam testResultParam = new TestResultParam();
//        testResultParam.setCodeId(id);
//        testResultParam.setResult(result);
//        testResultParam.setTestTime(new Date());
//        testResultParam.setTestUser(codeInfoParam.getCreateUser());
//        testResultParam.setUpdateAt(new Date());
//        testResultService.addResult(testResultParam);
//
//    }

    
public CodeInfoParam createCodeInfoParam(Map<String, Object> map) {

    CodeInfoParam codeInfoParam = new CodeInfoParam();
    codeInfoParam.setTitle(map.get("title").toString());
    codeInfoParam.setCreateUser(Integer.parseInt((String) map.get("createUser")));
    codeInfoParam.setContent(map.get("content").toString());
    return codeInfoParam;
}


}

