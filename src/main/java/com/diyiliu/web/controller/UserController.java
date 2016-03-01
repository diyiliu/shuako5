package com.diyiliu.web.controller;

import com.diyiliu.web.entity.User;
import com.diyiliu.web.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

/**
 * Description: UserController
 * Author: DIYILIU
 * Update: 2016-02-23 9:19
 */
@Controller
@RequestMapping(value = "/user/")
public class UserController {

    @Resource
    private UserService userService;


    @RequestMapping(value = "register", method = RequestMethod.POST)
    public String register(User user){

        userService.register(user);
        return "redirect:/anon/login.htm";
    }
}
