package com.ruhua.springtest.controller;

import com.alibaba.druid.util.StringUtils;
import com.ruhua.springtest.domain.CodeInfo;
import com.ruhua.springtest.domain.UserInfo;
import com.ruhua.springtest.service.CodeInfoService;
import com.ruhua.springtest.service.TeamCodeRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class TeamCodeRelationController {


    @Autowired
    CodeInfoService codeInfoService;

    @Autowired
    TeamCodeRelationService teamCodeRelationService;

    @GetMapping("/getCodeFromTeam")
    String getCodeFromTeam(@RequestParam("teamId") Integer teamId, Model model) {
        // 判断teamId不为空
        if (teamId == null) {
            model.addAttribute("error", "teamId is null");
            return "error";
        }
        // 找到teamId关联的代码 返回一个代码list
        List<CodeInfo> codeList = teamCodeRelationService.getCodeByTeamId(teamId);
        model.addAttribute("codes", codeList);
        return "codeInfoListForTeam";
    }

    @GetMapping("/addCodeFromTeam")
    String addCode(@RequestParam("codeId") Integer codeId, @RequestParam("userId") Integer userId, Model model, HttpSession session) {
        teamCodeRelationService.addCode(codeId, userId);
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
        String createUser = userInfo.getUsername();
        if (StringUtils.isEmpty(createUser)) {
            model.addAttribute("error", "参数不能为空");
            return "index";
        }
        // 从数据库里根据 title 和 createUser 去查询代码
        List<CodeInfo> codeInfos = codeInfoService.viewCodeByUser(createUser);
        model.addAttribute("codes", codeInfos);


        session.setAttribute("codeList", codeInfoService.viewCodeByUser(createUser));
        return "codeInfoList";
    }

    @GetMapping("/removeCodeFromTeam")
    String removeCode(@RequestParam("codeId") Integer codeId, @RequestParam("userId") Integer userId, Model model, HttpSession session) {
        teamCodeRelationService.removeCode(codeId, userId);
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
        String createUser = userInfo.getUsername();
        if (StringUtils.isEmpty(createUser)) {
            model.addAttribute("error", "参数不能为空");
            return "index";
        }
        // 从数据库里根据 title 和 createUser 去查询代码
        List<CodeInfo> codeInfos = codeInfoService.viewCodeByUser(createUser);
        model.addAttribute("codes", codeInfos);


        session.setAttribute("codeList", codeInfoService.viewCodeByUser(createUser));
        return "codeInfoList";
    }
}
