package com.chenjiahui.community.controller;

import com.chenjiahui.community.Model.Question;
import com.chenjiahui.community.Model.QuestionExample;
import com.chenjiahui.community.Model.User;
import com.chenjiahui.community.dto.CommentDtoUser;
import com.chenjiahui.community.dto.QuestionUser;
import com.chenjiahui.community.enums.CommentTypeEnum;
import com.chenjiahui.community.exception.CustomizeErrorCode;
import com.chenjiahui.community.exception.CustomizeException;
import com.chenjiahui.community.mapper.PublishMapper;
import com.chenjiahui.community.mapper.QuestionMapper;
import com.chenjiahui.community.mapper.UserMapper;
import com.chenjiahui.community.service.CommentService;
import com.chenjiahui.community.service.QuestionService;
import com.chenjiahui.community.service.QuestionUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
public class QuestionDetailsController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PublishMapper publishMapper;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private QuestionUserService questionUserService;


    @GetMapping("/questionDetails")
    public String question(@RequestParam(name = "questionId") Integer questionId, Model model){
        questionService.incView(questionId);
        QuestionUser questionUser=publishMapper.findQuestionDetailsId(questionId);
        if(questionUser==null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NO_FOUND);
        }

        List<Question>  moreQuestion=questionService.findMoreQuestion(questionUser);


        User user=userMapper.selectByPrimaryKey(questionUser.getCreator());
        questionUser.setUser(user);

       List<CommentDtoUser> commentUserList= commentService.ListByQuestionId(questionId, CommentTypeEnum.Question.getType());


        model.addAttribute("commentUserList",commentUserList);
        model.addAttribute("qu",questionUser);
        model.addAttribute("moreQuestion",moreQuestion);
        return "questionDetails";
    }
}
