package com.wff.demo.Controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.Map;

@Controller
public class HelloController {
    @RequestMapping("/hello")
    public String hello(){
        return "hello";
    }

    @RequestMapping("/success")
    public String success(Map<String,Object> map){
        map.put("hello","你好");
        map.put("user", Arrays.asList("zhangshan","lisi","wangwu"));
        return "success";
    }
    @RequestMapping("/add")
    public String add(){
        return "add";
    }
    @RequestMapping("/update")
    public String update(){
        return "update";
    }
    @RequestMapping("/toLogin")
    public String toLogin(){
        return "login";
    }
    @RequestMapping("/login")
    public String login(String name, String password , Model model) {
        /**
         * 编写Shiro认证操作
         */
        //获取Subject
        Subject subject = SecurityUtils.getSubject();
        //封装user
        UsernamePasswordToken token = new UsernamePasswordToken(name, password);
        //执行登录方法
        try {
            subject.login(token);
            //执行成功
            return "hello";
        } catch (UnknownAccountException e) {
            //用户名不存在
            model.addAttribute("msg", "用户名不存在");
            return "login";
        } catch (IncorrectCredentialsException e) {
            //密码错误
            model.addAttribute("msg", "密码不存在");
            return "login";
        }

    }
    @RequestMapping("/noAuth")
    public String nuAuth(){
        return "noAuth";
    }

}
