package com.ruhua.springtest.controller;

import com.ruhua.springtest.domain.CodeInfo;
import com.ruhua.springtest.domain.CommentInfo;
import com.ruhua.springtest.domain.UserInfo;
import com.ruhua.springtest.service.CodeInfoService;
import com.ruhua.springtest.service.CommentInfoService;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
public class CommentInfoController {

    @Autowired
    private CommentInfoService commentInfoService;

    @Autowired
    private CodeInfoService codeInfoService;

    //增加评论
    // 检查 content createUser codeId 不能为空
    // 如果为空 将错误信息放入到model 之中 并且返回到错误界面
    // 往数据库增加数据
    @PostMapping("/addComment")
    String addComment(@RequestParam Map<String, Object> comment, Model model,HttpSession session) {
        CommentInfo commentInfo = commentInfoFromMap(comment);
        if (commentInfo.getContent() == null || commentInfo.getCreateUser() == null || commentInfo.getCodeId() == null) {
            model.addAttribute("error", "content, createUser, and codeId cannot be empty");
            return "codeAndComment";
        }
        commentInfoService.addComment(commentInfo);

        UserInfo userInfo = (UserInfo)session.getAttribute("userInfo");
        model.addAttribute("userInfo",userInfo);
        // 从数据库里根据 title 和 createUser 去查询代码
        CodeInfo codeInfo = codeInfoService.getCodeByCodeAndCreateUser(commentInfo.getCodeId(), commentInfo.getOther());
        model.addAttribute("code",codeInfo);
        List<CommentInfo> commentInfoList = commentInfoService.getCommentsForCode(String.valueOf(codeInfo.getId()));
        model.addAttribute("commentInfoList", commentInfoList);
        // 刷新界面
        return "codeAndComment";
    }

    //删除评论  需要判断权限
    // 检查 content createUser codeId 不能为空
    // 如果为空 将错误信息放入到model 之中 并且返回到错误界面
    // 从数据库删除数据
    @GetMapping("/removeComment")
    String removeComment(@RequestParam("id") Integer commentId, Model model,HttpSession session) {

        if (commentId == null) {
            model.addAttribute("error", "id, createUser, and codeId cannot be empty");
            return "commentList";
        }
        commentInfoService.removeComment(commentId);
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
        Integer id = userInfo.getId();
        if (id == null) {
            return "index";
        }
        List<CommentInfo> commentPage = commentInfoService.getCommentPage(id);
        System.out.println(commentPage);
        model.addAttribute("comments", commentPage);
        return "commentList";
    }

    // 检查text 不为空
    // 查找评论内容包含text的评论
    // 返回一个集合
    @PostMapping("/search")
    String search(@RequestParam("text") String text, Model model) {
        if (text == null) {
            model.addAttribute("error", "text cannot be empty");
            return "codeInfoList";
        }
        model.addAttribute("comments", commentInfoService.search(text));
        return "commentList";
    }

    // 查看我的评论历史
    // 判断id不为空
    // 查找userid 等于id 的评论  返回一个集合
    @GetMapping("/getCommentList")
    String getCommentPage(Model model, HttpSession session) {
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
        Integer id = userInfo.getId();
        if (id == null) {
            return "index";
        }
        List<CommentInfo> commentPage = commentInfoService.getCommentPage(id);
        System.out.println(commentPage);
        model.addAttribute("comments", commentPage);
        return "commentList";
    }

    @GetMapping("/getCommentsForCode")
    String getCommentsForCode(Model model, HttpSession session, @RequestParam("codeId") String codeId) {
        List<CommentInfo> commentInfoList = commentInfoService.getCommentsForCode(codeId);
        model.addAttribute("commentInfoList", commentInfoList);
        return "getCommentsForCode";

    }

    //创建一个方法，输入是一个map，输出是一个commentInfoParam，从map中取出值放到返回值之中
    public CommentInfo commentInfoFromMap(Map<String, Object> map) {
        CommentInfo commentInfo = new CommentInfo();
        commentInfo.setContent((String) map.get("content"));
        commentInfo.setCreateUser(Integer.parseInt((String)  map.get("createUser")));
        commentInfo.setCodeId(Integer.parseInt((String) map.get("codeId")));
        if(map.get("other")!=null){
            commentInfo.setOther(Integer.parseInt((String) map.get("other")));
        }
        return commentInfo;
    }


}

