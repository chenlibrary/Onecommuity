package com.chenjiahui.community.mapper;

import com.chenjiahui.community.Model.Question;
import com.chenjiahui.community.Model.QuestionExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface QuestionExtMapper {
    int incView(Question record);

    int incComment(Question question);

    List<Question>  selectMoreQuestion(Question question);
}