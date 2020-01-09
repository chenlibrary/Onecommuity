package com.chenjiahui.community.service;

import com.chenjiahui.community.Model.Question;
import com.chenjiahui.community.dto.QuestionUser;
import com.chenjiahui.community.mapper.PublishMapper;
import com.chenjiahui.community.mapper.QuestionExtMapper;
import com.chenjiahui.community.mapper.QuestionMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {
    @Autowired
    private PublishMapper publishMapper;

    @Autowired
    private QuestionExtMapper questionExtMapper;

    @Autowired
    private QuestionMapper questionMapper;

    public List<Integer> findAllCount(){
        Integer page=0;
        page=publishMapper.findAllCount();
        List<Integer> listPage=new ArrayList<Integer>();
        if(page%4==0){
            page=page/4;
        }else{
            page=page/4+1;
        }
        for(int i=1;i<=page;i++){
            listPage.add(i);
        }
        return listPage;
    }

    public void createIfUpdate(Integer questionId, Question question) {
        System.out.println(questionId);
        System.out.println(question);
        if(questionId==0){
            question.setViewCount(0);
            question.setCommentCount(0);
            question.setLikeCount(0);
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            publishMapper.createOne(question);
        }else{
            question.setId(questionId);
            question.setGmtModified(question.getGmtCreate());
            publishMapper.update(question);
        }

    }
    public void incView(Integer questionId){
        Question question=new Question();
        question.setId(questionId);
        question.setViewCount(1);
        questionExtMapper.incView(question);
    }

    public List<Question> findMoreQuestion(QuestionUser questionUser) {

        String[] tags= StringUtils.split(questionUser.getTag(),",") ;
        String regexpTag= Arrays.stream(tags).collect(Collectors.joining("|"));
        Question question=new Question();
        question.setId(questionUser.getId());
        question.setTag(regexpTag);

        List<Question> questionList=questionExtMapper.selectMoreQuestion(question);

        return questionList;
    }
}
