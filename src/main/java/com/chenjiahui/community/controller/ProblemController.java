package com.chenjiahui.community.controller;

import com.chenjiahui.community.dto.QuestionPage;
import com.chenjiahui.community.mapper.PublishMapper;
import com.chenjiahui.community.mapper.UserMapper;
import com.chenjiahui.community.service.QuestionUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProblemController {
    @Autowired
    private PublishMapper publishMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionUserService questionUserService;
    @GetMapping("/problem/{action}")
    public String problem( @PathVariable(name = "action") String action
            ,Model model,HttpServletRequest request,
                        @RequestParam(name = "pageNum",defaultValue = "1") Integer pageNum,
                        @RequestParam(name = "size",defaultValue = "5") Integer size,
                        @RequestParam(name = "userId",defaultValue = "0") Integer userId){
       // List<Question> questionIdProblem=publishMapper.findIdProblem(userId);
        if("problems".equals(action)){
            model.addAttribute("problems","我的问题列表");

        }else if("reply".equals(action)){
            model.addAttribute("problems","回复我的列表");
        }
        QuestionPage questionPageList=questionUserService.findIdProblem(pageNum,size,userId);
        System.out.println("questionUserList:"+questionPageList);
        model.addAttribute("qul",questionPageList);

        return "problem";
    }
}
