package com.chenjiahui.community.service;

import com.chenjiahui.community.Model.*;
import com.chenjiahui.community.dto.CommentDtoUser;
import com.chenjiahui.community.enums.CommentTypeEnum;
import com.chenjiahui.community.exception.CustomizeErrorCode;
import com.chenjiahui.community.exception.CustomizeException;
import com.chenjiahui.community.mapper.CommentMapper;
import com.chenjiahui.community.mapper.QuestionExtMapper;
import com.chenjiahui.community.mapper.QuestionMapper;
import com.chenjiahui.community.mapper.UserMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@Transactional
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionExtMapper questionExtMapper;

    @Autowired
    private UserMapper userMapper;
    public void insert(Comment comment) {
        if(comment.getParentId()==null || comment.getParentId()==0){
            throw new CustomizeException(CustomizeErrorCode.TARGT_PARAM_NOT_FOUND);
        }
        if(comment.getType()==null|| !CommentTypeEnum.isExist(comment.getType())){
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }
        if(comment.getContent()==null||"".equals(comment.getContent())){
            throw new CustomizeException(CustomizeErrorCode.NO_COMMENT);
        }


        if(comment.getType()==CommentTypeEnum.COMMENT.getType()){
            //回复评论
            Comment dbComment=commentMapper.selectByPrimaryKey(comment.getParentId());
            if(dbComment==null){
               throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            //插入记得表的内容别为空
            comment.setLikeCount(0l);
            commentMapper.insert(comment);
        }else{
             //回复问题
            Question question =questionMapper.selectByPrimaryKey(comment.getParentId());
            if(question==null){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NO_FOUND);
            }
            comment.setLikeCount(0l);
            commentMapper.insert(comment);
            question.setCommentCount(1);
            questionExtMapper.incComment(question);
        }
    }

    public List<CommentDtoUser> ListByQuestionId(Integer questionId,Integer type) {
        CommentExample commentExample=new CommentExample();
        commentExample.createCriteria()
                .andParentIdEqualTo(questionId)
                .andTypeEqualTo(type);
        List<Comment> comments=commentMapper.selectByExample(commentExample);

        if(comments.size()==0){
            return new ArrayList<>();
        }
        //去重复评论人id
       Set<Integer> commentators=comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        List<Integer> userIDs=new ArrayList<>();
        userIDs.addAll(commentators);

        //评论人转map
        UserExample userExample=new UserExample();
        userExample.createCriteria()
                .andIdIn(userIDs);
        List<User> users=userMapper.selectByExample(userExample);
        Map<Integer,User> userMap=users.stream().collect(Collectors.toMap(user->user.getId(), user->user));

        //把Comment转CommentDtoUser
        List<CommentDtoUser> commentDtoUsers=comments.stream().map(comment -> {
           CommentDtoUser commentDtoUser=new  CommentDtoUser();
            BeanUtils.copyProperties(comment,commentDtoUser);
            commentDtoUser.setUser(userMap.get(comment.getCommentator()));
            return commentDtoUser;
        }).collect(Collectors.toList());

        return commentDtoUsers;
    }
}
