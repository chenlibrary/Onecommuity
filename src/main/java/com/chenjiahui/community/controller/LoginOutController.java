package com.chenjiahui.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginOutController {
    @GetMapping("/loginOut")
    public String loginOut(HttpServletRequest request, HttpServletResponse response){
        //注销session
        request.getSession().invalidate();
        //把cookie设置为null
        response.addCookie(new Cookie("token",null));

        return "redirect:/";

    }
}
