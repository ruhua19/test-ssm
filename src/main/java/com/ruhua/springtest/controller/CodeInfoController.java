package com.ruhua.springtest.controller;


import com.ruhua.springtest.domain.CodeInfo;
import com.ruhua.springtest.domain.CommentInfo;
import com.ruhua.springtest.domain.UserInfo;
import com.ruhua.springtest.param.CodeInfoParam;
import com.ruhua.springtest.param.TestResultParam;
import com.ruhua.springtest.service.*;

import com.ruhua.springtest.vo.CodeInfoVO;
import lombok.val;
import org.apache.commons.io.output.WriterOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpSession;
import javax.tools.*;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class CodeInfoController {

    @Autowired
    CodeInfoService codeInfoService;

    @Autowired
    TestResultService testResultService;

    @Autowired
    UserCodeShareService userCodeShareService;

    @Autowired
    TeamCodeRelationService teamCodeRelationService;

    @Autowired
    CommentInfoService commentInfoService;
// 增加版本

    @PostMapping("/addCode")

    public String addCode(@RequestParam Map<String, Object> code, Model model, HttpSession session) {
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
        model.addAttribute("userInfo", userInfo);
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
            if (codeInfoService.checkTitleExist(codeInfoParam.getTitle(), codeInfoParam.getCreateUser())) {
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

    @PostMapping("/testCode")

    public String testCode(@RequestParam Map<String, Object> code, Model model, HttpSession session) {
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
        model.addAttribute("userInfo", userInfo);
        CodeInfoParam codeInfoParam = createCodeInfoParam(code);
        // 判读参数 createUser 和 content不能为空 和 title 不能为空
        // 插入到代码表里
        // 如果为空 将错误信息放入到model 之中 并且返回到错误界面
        // 增加时 判断title不重复
        try {
            if (StringUtils.isEmpty(codeInfoParam.getCreateUser()) || StringUtils.isEmpty(codeInfoParam.getContent())
                    || StringUtils.isEmpty(codeInfoParam.getTitle())) {
                model.addAttribute("error", "参数不能为空");
                session.setAttribute("error","参数不能为空");
                return "createCode";
            }
            CodeInfoVO codeInfo = codeInfoService.viewCodeByTitleAndUser(codeInfoParam.getTitle(), codeInfoParam.getCreateUser().toString());
            if (codeInfo == null) {
                model.addAttribute("error", "代码不存在");
                session.setAttribute("error","参数不能为空");
                return "codeInfo";
            }
            String match = match(codeInfoParam.getContent());
            if (match == null) {
                model.addAttribute("error", "找不到类名");
                session.setAttribute("error","找不到类名");
            }
            String compiler = compiler(codeInfoParam.getContent(), match,codeInfo);
            model.addAttribute("code",codeInfo);
            model.addAttribute("userInfo",session.getAttribute("userInfo"));
            if (compiler == null) {
                return "codeResult";
            } else {
                model.addAttribute("error", compiler);
                session.setAttribute("error",compiler);

                return "codeResult";
            }
        } catch (Exception e) {
            System.out.println(e);
            model.addAttribute("error", "增加版本错误");
            return "codeInfo";
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
                           Model model, HttpSession session) {
        // 判读参数 title 和 createUser 不能为空
        if (StringUtils.isEmpty(title) || StringUtils.isEmpty(createUser)) {
            model.addAttribute("error", "参数不能为空");
            return "codeInfoList";
        }
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
        model.addAttribute("userInfo", userInfo);
        // 从数据库里根据 title 和 createUser 去查询代码
        model.addAttribute("code", codeInfoService.viewCodeByTitleAndUser(title, createUser));
        return "codeInfo";

    }

    /**
     * 修改代码
     *
     * @param model 模型
     * @return 修改结果
     */
    @PostMapping("/modifyCode")
    public String modifyCode(@RequestParam Map<String, Object> code, Model model,HttpSession session) {
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
            return "codeInfo";
        }
        codeInfoParam.setId(codeInfo.getId());
        codeInfoService.updateCode(codeInfoParam);
        model.addAttribute("code",codeInfo);
        model.addAttribute("userInfo",session.getAttribute("userInfo"));
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
    public String searchCode(@RequestParam("title") String title, @RequestParam("createUser") String createUser, Model model,HttpSession session) {
        // 判读参数 createUser  和 title 不能为空
        if (StringUtils.isEmpty(title) || StringUtils.isEmpty(createUser)) {
            model.addAttribute("error", "参数不能为空");
            return "codeInfoList";
        }
        // 删除title的代码
        CodeInfoVO codeByTitleAndCreateUser = codeInfoService.getCodeByTitleAndCreateUser(title, Integer.parseInt(createUser));
        teamCodeRelationService.removeCode(codeByTitleAndCreateUser.getId(),codeByTitleAndCreateUser.getCreateUser());
        testResultService.deleteCodeByCodeIdAndTestUser(codeByTitleAndCreateUser.getId(),Integer.parseInt(createUser));
        userCodeShareService.removeCode(codeByTitleAndCreateUser.getId(),codeByTitleAndCreateUser.getCreateUser());
        List<CommentInfo> commentsForCode = commentInfoService.getCommentsForCode(codeByTitleAndCreateUser.getId().toString());
        commentsForCode.forEach(
                o1->{commentInfoService.removeComment(o1.getId());}
        );
        codeInfoService.deleteCodeByTitleAndCreateUser(title, createUser);
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");


        // 从数据库里根据 title 和 createUser 去查询代码
        List<CodeInfo> codeInfos = codeInfoService.viewCodeByUser(createUser);
        model.addAttribute("codes", codeInfos);


        session.setAttribute("codeList", codeInfoService.viewCodeByUser(createUser));
        return "codeInfoList";
    }

    /**
     * 根据创建者查看代码
     *
     * @param model 模型
     * @return 代码内容
     */
    // 查看我的代码提交记录
    @GetMapping("/getCodeList")
    public String getCodeList(Model model, HttpSession session) {
        // createUser 不能为空
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
        String createUser = String.valueOf(userInfo.getId());
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
        codeInfoParam.setTitle(map.get("title").toString().replace("标题:",""));
        codeInfoParam.setCreateUser(Integer.parseInt( map.get("createUser").toString()));
        codeInfoParam.setContent(map.get("content").toString());
        return codeInfoParam;
    }

    public String match(String str) {

        Pattern pattern = Pattern.compile("public\\s+class\\s+(\\w+)\\s+");
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    public String compiler(String javaCode, String className, CodeInfoVO codeInfo) throws NoSuchMethodException, IOException, InvocationTargetException, IllegalAccessException {

        // 编译Java代码
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null);
        JavaFileObject javaFileObject = new JavaSourceFromString(className, javaCode);
        Iterable<? extends JavaFileObject> compilationUnits = Arrays.asList(javaFileObject);
        JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnostics, null, null, compilationUnits);
        boolean success = task.call();

         // 获取生成的类文件
        if (success) {
            byte[] classBytes = Files.readAllBytes(Paths.get(className + ".class"));
            Class<?> cls = new ClassLoader() {
                public Class<?> loadClass(byte[] b) {
                    return defineClass(null, b, 0, b.length);
                }
            }.loadClass(classBytes);
            PrintStream originalOut = System.out;
            StringWriter stringWriter = new StringWriter();
            PrintStream printStream = new PrintStream(new WriterOutputStream(stringWriter), true);

            System.setOut(printStream); // 将标准输出流重定向到printStream对象

            Method method = cls.getMethod("main", String[].class);
            method.invoke(null, (Object) null);
            System.setOut(originalOut);

            String output = stringWriter.toString(); // 获取标准输出流的所有内容


            TestResultParam build = TestResultParam.builder().codeId(codeInfo.getId()).result(output).code(codeInfo.getContent())
                    .testTime(new Date()).testUser(codeInfo.getCreateUser()).createAt(new Date()).updateAt(new Date()).build();
            testResultService.addResult(build);
            return output;
        } else {
            System.out.println("Compilation failed:");
            StringBuilder error = new StringBuilder();
            for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics()) {
                error.append(diagnostic.getMessage(null));
            }
            return error.toString();
        }
    }

}

