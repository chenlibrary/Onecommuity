package com.chenjiahui.community.controller;

import com.chenjiahui.community.dto.QuestionPage;
import com.chenjiahui.community.mapper.UserMapper;
import com.chenjiahui.community.service.QuestionService;
import com.chenjiahui.community.service.QuestionUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionUserService questionUserService;
    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(HttpServletRequest request, Model model,
                        @RequestParam(name = "pageNum",defaultValue = "1") Integer pageNum,
                        @RequestParam(name = "size",defaultValue = "5") Integer size) {
        QuestionPage questionPageList=questionUserService.find(pageNum,size);
        System.out.println("questionUserList:"+questionPageList);
        model.addAttribute("qul",questionPageList);
        return "index";
    }
}
