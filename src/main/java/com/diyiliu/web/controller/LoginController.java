package com.diyiliu.web.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Description: LoginController
 * Author: DIYILIU
 * Update: 2016-02-22 14:31
 */

@Controller
@RequestMapping(value = "/")
public class LoginController {

    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String index() {

        return "/index";
    }

    @RequestMapping(value = "anon/login", method = RequestMethod.GET)
    public String login() {

        return "/login";
    }


    @RequestMapping(value = "anon/register", method = RequestMethod.GET)
    public String register() {

        return "/register";
    }


    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String login(HttpServletRequest request, Model model) {

        String exceptionClassName = (String) request.getAttribute("shiroLoginFailure");

        if (UnknownAccountException.class.getName().equals(exceptionClassName)
                || IncorrectCredentialsException.class.getName().equals(exceptionClassName)) {

            model.addAttribute("error", "用户名/密码错误");
            return "/login";
        } else if (ExcessiveAttemptsException.class.getName().equals(exceptionClassName)) {

            model.addAttribute("error", "登录错误次数超限，请稍后再试！");
            return "/login";

        } else if (exceptionClassName != null) {
            model.addAttribute("error", "登录异常：" + exceptionClassName);

            return "/login";
        }

        return "redirect:/index.htm";
    }

    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public String logout(HttpSession session) {

        SecurityUtils.getSubject().logout();


        return "redirect:/index.htm";
    }
}
