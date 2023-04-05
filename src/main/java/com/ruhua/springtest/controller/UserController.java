package com.ruhua.springtest.controller;


import com.ruhua.springtest.param.UserParam;
import com.ruhua.springtest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    //登录
    @PostMapping("/users")
    public String login(@RequestBody UserParam userParam) {

        userService.getUser(userParam);
        return "static/login";
    }

    @GetMapping("/test")
    public String test() {
        System.out.println("laile ");
        UserParam userParam = new UserParam();
        userParam.setId(1L);
        userService.getUser(userParam);
        return "static/login";
    }



}
