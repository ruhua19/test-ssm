package com.ruhua.springtest.controller;

import com.ruhua.springtest.domain.CodeInfo;
import com.ruhua.springtest.domain.UserCodeShareInfo;
import com.ruhua.springtest.domain.UserInfo;
import com.ruhua.springtest.mapper.UserCodeShareMapper;
import com.ruhua.springtest.service.CodeInfoService;
import com.ruhua.springtest.service.UserCodeShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class UserCodeShareController {

   @Autowired
   UserCodeShareService userCodeShareService;

   @Autowired
    CodeInfoService codeInfoService;
    @GetMapping("/getCodeFromShare")
    String getCode( Model model) {
        // 判断teamId不为空
        List<CodeInfo> code = userCodeShareService.getCode();
        model.addAttribute("codes", code);
        return "codeInfoListForShare";
    }

    @GetMapping("/addCode")
    String addCode(@RequestParam("codeId") Integer codeId, @RequestParam("userId") Integer userId, Model model, HttpSession session){
        userCodeShareService.addCode(codeId,userId);
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

    @GetMapping("/removeCode")
    String removeCode(@RequestParam("codeId") Integer codeId,@RequestParam("userId") Integer userId,Model model, HttpSession session){
        userCodeShareService.removeCode(codeId,userId);
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
}
