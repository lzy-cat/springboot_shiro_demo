package com.shiro.controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    @RequestMapping("/")
    public String index(Model model){
        model.addAttribute("msg","首页");
        return "index";
    }
    @RequestMapping("user/add")
    public String add(Model model){
        return "user/add";
    }
    @RequestMapping("user/update")
    public String update(Model model){
        return "user/update";
    }
    @RequestMapping("login")
    public String login(String name,String password, Model model){
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(name, password);
        try {
            subject.login(token);
            return "index";
        } catch (UnknownAccountException uae) {
            model.addAttribute("msg" ,"用戶名错误");
            return "login";
        } catch (IncorrectCredentialsException ice) {
            model.addAttribute("msg" ,"密码错误");
            return "login";
        }

    }
    @RequestMapping("toLogin")
    public String toLogin(){
        return "login";
    }
}
