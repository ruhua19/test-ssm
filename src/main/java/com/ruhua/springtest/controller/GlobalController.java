package com.ruhua.springtest.controller;

import com.ruhua.springtest.domain.CodeInfo;
import com.ruhua.springtest.domain.CommentInfo;
import com.ruhua.springtest.domain.UserInfo;
import com.ruhua.springtest.service.CodeInfoService;
import com.ruhua.springtest.service.CommentInfoService;
import com.ruhua.springtest.vo.CodeInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class GlobalController {

    @Autowired
    CodeInfoService codeInfoService;

    @Autowired
    CommentInfoService commentInfoService;

    @RequestMapping("/index")
    public String index(){
        return "index";
    }

    @RequestMapping("/createCode")
    public String createCode(HttpSession session, Model model){
        UserInfo userInfo = (UserInfo)session.getAttribute("userInfo");
        model.addAttribute("userInfo",userInfo);
        return "createCode";
    }



    @RequestMapping("/CodeAndComment")
    public String getCOdeAndComment(@RequestParam("createUser") Integer createUser, @RequestParam("title")Integer title, HttpSession session,Model model){
        // 判读参数 title 和 createUser 不能为空
        if (StringUtils.isEmpty(title) || StringUtils.isEmpty(createUser)) {
            model.addAttribute("error", "参数不能为空");
            return "codeInfoList";
        }
        UserInfo userInfo = (UserInfo)session.getAttribute("userInfo");
        model.addAttribute("userInfo",userInfo);
        // 从数据库里根据 title 和 createUser 去查询代码
        CodeInfo codeInfo = codeInfoService.getCodeByCodeAndCreateUser(title, createUser);
        model.addAttribute("code",codeInfo);
        List<CommentInfo> commentInfoList = commentInfoService.getCommentsForCode(String.valueOf(codeInfo.getId()));
        model.addAttribute("commentInfoList", commentInfoList);
        return "CodeAndComment";
    }


    @RequestMapping("/forRegister")
    public String forRegister(){

        return "register";
    }

    @RequestMapping("/forCreateTeam")
    public String forCreateTeam( HttpSession session,Model model){
        UserInfo userInfo = (UserInfo)session.getAttribute("userInfo");
        model.addAttribute("userInfo",userInfo);
        return "createTeam";
    }
}
