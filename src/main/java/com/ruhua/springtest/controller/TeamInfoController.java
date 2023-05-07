package com.ruhua.springtest.controller;

import com.ruhua.springtest.domain.CodeInfo;
import com.ruhua.springtest.domain.TeamInfo;
import com.ruhua.springtest.domain.UserInfo;
import com.ruhua.springtest.param.TeamInfoParam;
import com.ruhua.springtest.service.CodeInfoService;
import com.ruhua.springtest.service.TeamInfoService;
import com.ruhua.springtest.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.lang.model.element.VariableElement;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class TeamInfoController {
    @Autowired
    TeamInfoService teamInfoService;
    @Autowired
    UserInfoService userInfoService;
    @Autowired
    CodeInfoService codeInfoService;

    // 创建团队
    @PostMapping("/createTeam")
    String createTeam(@RequestParam Map<String, Object> team, Model model) {
        TeamInfoParam teamInfoParam = createTeamInfoParamFromMap(team);
        // 判断name 和 owner 不为空
        if (teamInfoParam.getName() == null || teamInfoParam.getOwner() == null) {
            // 如果为空 将错误信息放入到model 之中 并且返回到错误界面
            model.addAttribute("error", "name or owner is null");
            return "createTeam";
        }
        // 判断name 不重复
        TeamInfo teamInfo = teamInfoService.getTeamInfoByName(teamInfoParam.getName(),teamInfoParam.getOwner());
        if (teamInfo != null) {
            model.addAttribute("error", "name is already exist");
            return "createTeam";
        }
        // 修改用户表 增加teamId 为数据库通过name查出来的id
        teamInfoService.createTeam(teamInfoParam);
        TeamInfo teamInfoByName = teamInfoService.getTeamInfoByName(teamInfoParam.getName(), teamInfoParam.getOwner());
        userInfoService.updateTeamFromUser(teamInfoByName.getId(),teamInfoParam.getOwner());
        return "teamInfo";
    }
    @PostMapping("/removeTeam")
    String removeTeam(@RequestParam Map<String, Object> team, Model model) {
        TeamInfoParam teamInfoParam = createTeamInfoParamFromMap(team);
        // 判断name 和 owner 不为空
        if (teamInfoParam.getName() == null || teamInfoParam.getOwner() == null) {
            // 如果为空 将错误信息放入到model 之中 并且返回到错误界面
            model.addAttribute("error", "name or owner is null");
            return "removeTeam";
        }
        // 判断name 查出来的owner为当前owner
        TeamInfo teamInfo = teamInfoService.getTeamInfoByName(teamInfoParam.getName(),teamInfoParam.getOwner());
        if (teamInfo == null || !teamInfo.getOwner().equals(teamInfoParam.getOwner())) {
            model.addAttribute("error", "name or owner is incorrect");
            return "removeTeam";
        }
        // 遍历用户 将用户的teamId 作为空值 当用户的teamId 等于当前teamId 值时
        userInfoService.removeTeamIdByTeamId(teamInfo.getId());
        teamInfoService.removeTeam(teamInfo.getId());
        return "index";
    }

    @PostMapping("/addUser")
    String addUser(@RequestParam("userId") Integer userId, @RequestParam("teamId") Integer teamId, Model model) {
        // 根据userId 查询用户
        UserInfo userById = userInfoService.getUserById(userId);

        // 用户判断不为空
        if (userById == null) {
            model.addAttribute("error", "user is not exist");
            return "error";
        }
        // 往用户中增加teamId 属性
        userById.setTeamId(teamId);
        userInfoService.updateTeamFromUser(teamId,userById.getId());
        return "teamInfo";
    }

    @PostMapping("/removeUser")
    String removeUser(@RequestParam("userId")Integer userId,  @RequestParam("teamId")Integer teamId, Model model) {
        // 根据userId 查询用户
        UserInfo userById = userInfoService.getUserById(userId);
        // 用户判断不为空
        if (userById == null) {
            model.addAttribute("error", "user is not exist");
            return "error";
        }
        // 往用户中删除teamId 属性
        if (userById.getTeamId() != null && userById.getTeamId().equals(teamId)) {
            userById.setTeamId(null);
            userInfoService.updateTeamFromUser(null,userById.getId());
        }
        return "teamInfo";
    }
    @GetMapping("/getCodeFromTeam")
    String getCodeFromTeam( @RequestParam("teamId") Integer teamId, Model model) {
        // 判断teamId不为空
        if (teamId == null) {
            model.addAttribute("error", "teamId is null");
            return "error";
        }
        // 找到teamId关联的代码 返回一个代码list
        List<CodeInfo> codeList = codeInfoService.getCodeByTeamId(teamId);
        model.addAttribute("codes", codeList);
        return "codeInfoList";
    }
    // 创建一个方法 输入是一个map 输出是一个TeamInfoParam 从map中取出值 放到返回值之中
    public TeamInfoParam createTeamInfoParamFromMap(Map<String, Object> map) {
        TeamInfoParam teamInfoParam = new TeamInfoParam();
        // 从map中取出name和owner的值，放到teamInfoParam中
        teamInfoParam.setName((String) map.get("name"));
        teamInfoParam.setOwner(Integer.parseInt( (String)map.get("owner")));
        return teamInfoParam;
    }
    @GetMapping("/getTeam")
    public  String getTeam( Model model, HttpSession session){
        UserInfo userInfo =(UserInfo) session.getAttribute("userInfo");
        Integer id =    userInfo.getId();
        TeamInfo teamInfo = teamInfoService.getTeamByUser(id);
        ArrayList<TeamInfo> objects = new ArrayList<>();
        objects.add(teamInfo);
        model.addAttribute("teams",objects);
        return "teamInfo";
    }
}


