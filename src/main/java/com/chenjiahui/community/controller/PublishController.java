package com.chenjiahui.community.controller;

import com.chenjiahui.community.Model.Question;
import com.chenjiahui.community.Model.User;
import com.chenjiahui.community.dto.QuestionUser;
import com.chenjiahui.community.mapper.PublishMapper;
import com.chenjiahui.community.mapper.UserMapper;
import com.chenjiahui.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {
    @Autowired
    private PublishMapper publishMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionService questionService;
    @GetMapping("/publish/{questionId}")
    public String publishQuestionId(@PathVariable(name = "questionId") Integer questionId, Model model){
        QuestionUser  questionUser=publishMapper.findQuestionDetailsId(questionId);
        model.addAttribute("title",questionUser.getTitle());
        model.addAttribute("description",questionUser.getDescription());
        model.addAttribute("tag",questionUser.getTag());
        model.addAttribute("id",questionUser.getId());
        return "publish";
    }
    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }
    @PostMapping("/publish")
    public String doPublish(@RequestParam(name="title") String title,
                            @RequestParam(name="description") String description,
                            @RequestParam(name="tag") String tag,
                            @RequestParam(name="questionId" ,defaultValue = "0") Integer questionId ,
                            HttpServletRequest request, Model model){
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        if(title==null){
            model.addAttribute("error","title不能为空");
            return "publish";
        }
        if(description==null){
            model.addAttribute("error","description不能为空");
            return "publish";
        }
        if(tag==null){
            model.addAttribute("error","tag不能为空");
            return "publish";
        }

        User user=(User) request.getSession().getAttribute("user");

        if(user==null){
            System.out.println("用户为空");
            model.addAttribute("error","用户未登陆");
            return "publish";
        }
        Question question=new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);

        question.setCreator(user.getId());
        questionService.createIfUpdate(questionId,question);
        return "redirect:/";
    }
}
