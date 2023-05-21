package com.ruhua.springtest.controller;

import com.ruhua.springtest.domain.CodeInfo;
import com.ruhua.springtest.domain.CommentInfo;
import com.ruhua.springtest.domain.TeamInfo;
import com.ruhua.springtest.domain.UserInfo;
import com.ruhua.springtest.service.CodeInfoService;
import com.ruhua.springtest.service.CommentInfoService;
import com.ruhua.springtest.service.TeamInfoService;
import com.ruhua.springtest.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class GlobalController {

    @Autowired
    CodeInfoService codeInfoService;

    @Autowired
    CommentInfoService commentInfoService;

    @Autowired
    UserInfoService userInfoService;

    @Autowired
    TeamInfoService teamInfoService;


    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    @RequestMapping("/createCode")
    public String createCode(HttpSession session, Model model) {
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
        model.addAttribute("userInfo", userInfo);
        return "createCode";
    }

    @GetMapping("/changeUserInfo")
    public String changeUserInfo(HttpSession session, Model model) {
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
        model.addAttribute("userInfo", userInfo);
        return "changeUserInfo";
    }

    @GetMapping("/changePassword")
    public String changePassword(HttpSession session, Model model) {
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
        model.addAttribute("userInfo", userInfo);
        return "changePassword";
    }


    @RequestMapping("/CodeAndComment")
    public String getCOdeAndComment(@RequestParam("createUser") Integer createUser, @RequestParam("title") Integer title, HttpSession session, Model model) {
        // 判读参数 title 和 createUser 不能为空
        if (StringUtils.isEmpty(title) || StringUtils.isEmpty(createUser)) {
            model.addAttribute("error", "参数不能为空");
            return "codeInfoList";
        }
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
        model.addAttribute("userInfo", userInfo);
        // 从数据库里根据 title 和 createUser 去查询代码
        CodeInfo codeInfo = codeInfoService.getCodeByCodeAndCreateUser(title, createUser);
        model.addAttribute("code", codeInfo);
        List<CommentInfo> commentInfoList = commentInfoService.getCommentsForCode(String.valueOf(codeInfo.getId()));
        model.addAttribute("commentInfoList", commentInfoList);
        return "CodeAndComment";
    }


    @RequestMapping("/forRegister")
    public String forRegister() {

        return "register";
    }

    @RequestMapping("/forCreateTeam")
    public String forCreateTeam(HttpSession session, Model model) {
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
        model.addAttribute("userInfo", userInfo);
        return "createTeam";
    }

    @GetMapping("/searchForUser")
    public String searchForUser(@RequestParam("search") String search,Model model) {
        List<UserInfo> userInfos = userInfoService.searchForUser(search);

        model.addAttribute("users",userInfos);
        return "userInfoList";

    }

    @GetMapping("/searchForCode")
    public String searchForCode(@RequestParam("search") String search,Model model) {
        List<CodeInfo> codeInfos = codeInfoService.searchForCode(search);
        model.addAttribute("codes" ,codeInfos);
        return "codeInfoListForShare";
    }

    @GetMapping("/searchForTeam")
    public String searchForTeam(@RequestParam("search") String search,Model model,HttpSession session) {
        UserInfo userInfo = (UserInfo)session.getAttribute("userInfo");
        model.addAttribute("userInfo",userInfo);
        List<TeamInfo> teamInfos = teamInfoService.searchForTeam(search);
        model.addAttribute("teams",teamInfos);
        session.setAttribute("search",search);
        return "teamListForShare";
    }

    @GetMapping("/searchForComment")
    public String searchForComment(@RequestParam("search") String search,Model model) {
        List<CommentInfo> commentInfoList = commentInfoService.search(search);
        model.addAttribute("comments",commentInfoList);
        return "commentListForShare";
    }

    @ResponseBody
    @PostMapping("/getError")
    public String getError(Model model,HttpSession session) {

        Object error = session.getAttribute("error");
        return error.toString();
    }

    @GetMapping("/viewCode")
    public String viewCode(@RequestParam("createUser") Integer createUser, @RequestParam("title") Integer title, HttpSession session, Model model) {
        // 判读参数 title 和 createUser 不能为空
        if (StringUtils.isEmpty(title) || StringUtils.isEmpty(createUser)) {
            model.addAttribute("error", "参数不能为空");
            return "codeInfoList";
        }
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
        model.addAttribute("userInfo", userInfo);
        // 从数据库里根据 title 和 createUser 去查询代码
        CodeInfo codeInfo = codeInfoService.getCodeByCodeAndCreateUser(title, createUser);
        model.addAttribute("code", codeInfo);
        List<CommentInfo> commentInfoList = commentInfoService.getCommentsForCode(String.valueOf(codeInfo.getId()));
        model.addAttribute("commentInfoList", commentInfoList);
        return "viewCode";
    }

    @GetMapping("/teamInfo")
    public String teamInfo( Model model, HttpSession session){
        UserInfo userInfo =(UserInfo) session.getAttribute("userInfo");
        Integer id =    userInfo.getId();
        TeamInfo teamInfo = teamInfoService.getTeamByUser(id);
        ArrayList<TeamInfo> objects = new ArrayList<>();
        objects.add(teamInfo);
        model.addAttribute("teams",objects);
        Integer teamInfoId = teamInfo.getId();
        List<UserInfo> userInfoByTeamId = userInfoService.getUserInfoByTeamId(teamInfoId);
        userInfoByTeamId.forEach(userInfo1 -> {
            userInfo1.setTeamTitle(teamInfoService.getTeamByUser(userInfo1.getId()).getName());
        });
        model.addAttribute("users",userInfoByTeamId);
        return "teamInfo";
    }

}
