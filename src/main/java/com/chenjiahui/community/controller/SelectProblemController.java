package com.chenjiahui.community.controller;

import com.chenjiahui.community.dto.QuestionPage;
import com.chenjiahui.community.service.QuestionUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SelectProblemController {
    @Autowired
    private QuestionUserService questionUserService;
    @GetMapping("/selectProblem")
    public String doSelectProblem(Model model, @RequestParam("select") String selsect, @RequestParam(name = "pageNum",defaultValue = "1") Integer pageNum,
                                  @RequestParam(name = "size",defaultValue = "5") Integer size){
        String selsectString="%"+selsect+"%";
        QuestionPage questionPageList=questionUserService.findSelectAll(pageNum,size,selsectString);
        model.addAttribute("select",selsect);
        model.addAttribute("qul",questionPageList);
        return "selectProblem";
    }
}
