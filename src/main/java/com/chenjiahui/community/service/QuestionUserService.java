package com.chenjiahui.community.service;

import com.chenjiahui.community.Model.Question;
import com.chenjiahui.community.Model.User;
import com.chenjiahui.community.Model.UserExample;
import com.chenjiahui.community.dto.QuestionPage;
import com.chenjiahui.community.dto.QuestionUser;
import com.chenjiahui.community.mapper.PublishMapper;
import com.chenjiahui.community.mapper.QuestionExtMapper;
import com.chenjiahui.community.mapper.QuestionMapper;
import com.chenjiahui.community.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionUserService {
    @Autowired
    private PublishMapper publishMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionExtMapper questionExtMapper;

    //问题详情
    public QuestionUser findIdQuestionDetails(){
        return  null;
    }
    //首页
    public QuestionPage find(Integer pageNum,Integer size){
        Integer allCount=publishMapper.findAllCount();
        Integer[] pageAllNowpageNum=pageAll(allCount,pageNum,size);
        List<QuestionUser> questionUser=publishMapper.findAll((pageAllNowpageNum[1]-1)*size,size);
        return squestionPage(questionUser,pageAllNowpageNum[0],pageAllNowpageNum[1],size);
    }
    //我的问题
    public QuestionPage findIdProblem(Integer pageNum,Integer size,Integer userId){
        Integer allCount=publishMapper.findIdProblemAll(userId);
        Integer[] pageAllNowpageNum=pageAll(allCount,pageNum,size);
        System.out.println(pageAllNowpageNum[1]);
        List<QuestionUser> questionUser=publishMapper.findIdProblem(userId,(pageAllNowpageNum[1]-1)*size,size);
        return squestionPage(questionUser,pageAllNowpageNum[0],pageAllNowpageNum[1],size);
    }
    //查询按钮
    public QuestionPage findSelectAll(Integer pageNum,Integer size,String selsect){
        Integer allCount=publishMapper.findSelectAllCount(selsect);
        Integer[] pageAllNowpageNum=pageAll(allCount,pageNum,size);
        List<QuestionUser> questionUser=publishMapper.findSelectAll(selsect,(pageAllNowpageNum[1]-1)*size,size);
        return squestionPage(questionUser,pageAllNowpageNum[0],pageAllNowpageNum[1],size);
    }



    //返回总页数
    public Integer[] pageAll(Integer allCount,Integer pageNum,Integer size){
        Integer[] pageAllNowpageNum=new Integer[2];
        Integer pageAll=0;
        Integer nowpageNum=pageNum;
        if(allCount%size==0){
            pageAll=allCount/size;
        }else{
            pageAll=allCount/size+1;
        }
        if(pageNum>pageAll){
            nowpageNum=pageAll;
        }else if(pageNum<1){
            nowpageNum=1;
        }

        if(nowpageNum==0){
            nowpageNum=1;
        }
        pageAllNowpageNum[0]=pageAll;
        pageAllNowpageNum[1]=nowpageNum;
        return  pageAllNowpageNum;
    }
    //返回QuestionPage对象
    public QuestionPage squestionPage(List<QuestionUser> questionUser,Integer pageAll,Integer nowpageNum,Integer size){
        List<QuestionUser> questionUsers=new ArrayList<QuestionUser>();
        QuestionPage questionPage=new QuestionPage();
        for(QuestionUser question:questionUser){
            User user=userMapper.selectByPrimaryKey(question.getCreator());
           // User user=userMapper.findUserId(question.getCreator());
            question.setUser(user);
            if(question.getCommentCount()==null){
                question.setCommentCount(0);
            }
            if(question.getViewCount()==null){
                question.setViewCount(0);
            }

            question.getViewCount();
            questionUsers.add(question);
        }
        questionPage.setQuestionUser(questionUsers);

        questionPage.setAll(pageAll,nowpageNum,size);
        return questionPage;
    }
}
