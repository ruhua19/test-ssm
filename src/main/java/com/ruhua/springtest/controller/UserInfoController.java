package com.ruhua.springtest.controller;

import com.ruhua.springtest.domain.UserInfo;
import com.ruhua.springtest.param.UserInfoParam;
import com.ruhua.springtest.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;


    // 查看当前个人信息
    @PostMapping("/userInfo")
    public String userInfo(@RequestParam Map<String,Object>user, Model model) {
        UserInfoParam userInfoParam = mapToUserInfoParam(user);
        // 获取当前用户id
        Integer userId = userInfoParam.getId();
        if (userId == null) {
            model.addAttribute("error", "用户不存在");
            return "error";
        }
        // 根据id查询用户信息
        UserInfo userInfo = userInfoService.getUserInfoById(userId);
        if(userInfo==null){
            model.addAttribute("error", "用户不存在 ");
            return "error";
        }
        // 将用户信息存入model中
        model.addAttribute("userInfo", userInfo);
        // 返回个人信息页面
        return "userInfo";
    }

    // 用户登录功能
    @PostMapping("/login")
    public String login( @RequestParam Map<String,Object> user, Model model,HttpSession session) {
        UserInfoParam userInfoParam = mapToUserInfoParam(user);
        // 获取用户名和密码
        String username = userInfoParam.getUsername();
        String password = userInfoParam.getPassword();
        if (username == null || username.length() == 0 || password == null || password.length() == 0) {
            model.addAttribute("error", "用户名或者密码为空");
            System.out.println("用户名或者密码为空");
            return "login";
        }
        // 根据用户名和密码查询用户信息
        UserInfo userInfo = userInfoService.getUserInfoByUsernameAndPassword(username, password);
        // 判断用户信息是否存在
        if (userInfo != null) {
            // 将用户信息存入model中
            model.addAttribute("userInfo", userInfo);
            session.setAttribute("userInfo",userInfo);
            System.out.println("成功");
            // 返回首页
            return "index";
        } else {
            // 用户信息不存在，返回登录页面

            model.addAttribute("error", "用户不存在");
            System.out.println("用户不存在");
            return "login";
        }
    }

    // 用户注册功能
    @PostMapping("/register")
    public String register(@RequestParam Map<String,Object>user, Model model) {
        UserInfoParam userInfoParam = mapToUserInfoParam(user);
        // 获取用户名和密码
        String username = userInfoParam.getUsername();
        String password = userInfoParam.getPassword();
        // 根据用户名和密码查询用户信息
        UserInfo userInfo = userInfoService.getUserInfoByUsernameAndPassword(username, password);
        // 判断用户信息是否存在
        if (userInfo != null) {
            // 用户信息已存在，返回注册页面
            model.addAttribute("error", "用户信息已存在 ");
            return "register";
        } else {
            // 用户信息不存在，进行注册
            userInfoService.register(userInfoParam);
            // 将用户信息存入model中

            // 返回登录页面
            return "login";
        }
    }

    // 密码修改功能
    @PostMapping("/changePassword")
    public String changePassword(@RequestParam Map<String,Object>user, Model model) {
        UserInfoParam userInfoParam = mapToUserInfoParam(user);
        // 获取当前用户id
        Integer userId = userInfoParam.getId();
        // 获取新密码
        String password = userInfoParam.getPassword();

        if (userInfoParam.getConfirmPassword() == null || userInfoParam.getConfirmPassword().length() == 0 || password == null || password.length() == 0 || userId == null) {
            model.addAttribute("error", "修改密码错误 ");
            return "changePassword";
        }
        UserInfo userInfo = userInfoService.getUserInfoById(userId);
        if(!Objects.equals(userInfo.getPassword(), userInfoParam.getPassword())){
            model.addAttribute("error", "密码错误 ");
            return "changePassword";
        }
        // 修改密码
        userInfoService.changePassword(userId, userInfoParam.getConfirmPassword());
        // 根据id查询用户信息

        // 将用户信息存入model中
        model.addAttribute("userInfo", userInfo);
        // 返回登录页面
        return "login";
    }

    // 信息修改功能
    @PostMapping("/changeUserInfo")
    public String updateInfo(@RequestParam Map<String,Object>user, Model model,HttpSession session) {
        UserInfoParam userInfoParam = mapToUserInfoParam(user);
        // 获取当前用户id
        Integer userId = userInfoParam.getId();
        if(userId == null){
            model.addAttribute("error", "用户不存在 ");
            return "changeUserInfo";
        }
        // 更新用户信息
        UserInfo userInfo = userInfoService.getUserInfoById(userId);
        if(userInfo==null){
            model.addAttribute("error", "用户不存在 ");
            return "changeUserInfo";
        }
        userInfoService.updateUserInfo(userId, userInfoParam);
        // 将用户信息存入model中
        model.addAttribute("userInfo", userInfo);
        session.setAttribute("userInfo",userInfo);
        // 返回个人信息页面
        return "userInfo";
    }

    @GetMapping("/getUserInfo")
    String getUserInfo( Model model,HttpSession session) {
        UserInfo userInfo =(UserInfo) session.getAttribute("userInfo");
        Integer id =    userInfo.getId();
        // 查找用户  判断text不为空
        // 查找name中包含text的的用户
        UserInfo user = userInfoService.getUserInfoById(id);
        model.addAttribute("userInfo", user);
        return "userInfo";
    }

public UserInfoParam mapToUserInfoParam(Map<String, Object> map) {
    UserInfoParam userInfoParam = new UserInfoParam();
    // 从map中取出值放到返回值之中
    userInfoParam.setUsername((String) map.get("username"));
    userInfoParam.setPassword((String) map.get("password"));
    String id = map.get("id")==null?"":map.get("id").toString();
    if(id!=null&& !id.equals("")){

        userInfoParam.setId(Integer.valueOf(id));
    }
    userInfoParam.setEmail((String) map.get("email"));
    userInfoParam.setConfirmPassword((String) map.get("confirmPassword"));
    return userInfoParam;
}


}



