package com.chenjiahui.community.controller;

import com.chenjiahui.community.Model.Comment;
import com.chenjiahui.community.Model.User;
import com.chenjiahui.community.dto.CommentDto;
import com.chenjiahui.community.dto.CommentDtoUser;
import com.chenjiahui.community.dto.ResultDto;
import com.chenjiahui.community.enums.CommentTypeEnum;
import com.chenjiahui.community.exception.CustomizeErrorCode;
import com.chenjiahui.community.exception.CustomizeException;
import com.chenjiahui.community.mapper.CommentMapper;
import com.chenjiahui.community.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CommentController {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private CommentService commentService;

    @ResponseBody
    @RequestMapping( value = "/comment" ,method=RequestMethod.POST)
    public Object post(@RequestBody CommentDto commentDto, HttpServletRequest request){
        System.out.println("我进去了");
        User user=(User) request.getSession().getAttribute("user");
        if(user==null){
            return  new ResultDto().errorOf(new CustomizeException(CustomizeErrorCode.NO_LOGIN));
        }
        if(commentDto==null|| StringUtils.isBlank(commentDto.getContent())){
            return  new ResultDto().errorOf(new CustomizeException(CustomizeErrorCode.ISNULL_CONTENT));
        }

        Comment comment=new Comment();
        comment.setParentId(commentDto.getParentId());
        comment.setContent(commentDto.getContent());
        comment.setType(commentDto.getType());
        comment.setCommentator(user.getId());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());
        commentService.insert(comment);
        return new ResultDto().okOf();
    }

    @ResponseBody
    @RequestMapping( value = "/comment/{parentId}" ,method=RequestMethod.GET)
    public ResultDto<List<CommentDtoUser>> doGet(@PathVariable(name = "parentId") Integer parentId){
        System.out.println("进去了");
        List<CommentDtoUser> commentDtoUserList=commentService.ListByQuestionId(parentId, CommentTypeEnum.COMMENT.getType());
        return ResultDto.okOf(commentDtoUserList);
    }
}
