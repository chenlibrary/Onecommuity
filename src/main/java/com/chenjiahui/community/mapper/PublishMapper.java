package com.chenjiahui.community.mapper;

import com.chenjiahui.community.Model.Question;
import com.chenjiahui.community.dto.QuestionUser;
import org.apache.ibatis.annotations.*;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

@Mapper
public interface PublishMapper {
    @Insert("insert into question(title,description,gmt_create,gmt_modified,creator,comment_count,view_count,like_count,tag) values (#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{commentCount},#{viewCount},#{likeCount},#{tag})")
    void createOne(Question question);

    @Select("select * from question limit #{pageNum},#{size}")
    List<QuestionUser> findAll(@Param("pageNum")Integer pageNum, @Param("size")Integer size);
    @Select("select count(1) from question")
    Integer findAllCount();

    @Select("SELECT * FROM question where creator=#{userId} limit #{pageNum},#{size}")
    List<QuestionUser> findIdProblem(@Param("userId")Integer userId,@Param("pageNum")Integer pageNum, @Param("size")Integer size);
    @Select("select count(1) from question where creator=#{userId}")
    Integer findIdProblemAll(@Param("userId")Integer userId);

    @Select("SELECT count(1) FROM question where title like #{selsect} or description like #{selsect}")
    Integer findSelectAllCount(@Param("selsect")String selsect);
    @Select("SELECT * FROM question where title like #{selsect} or description like #{selsect} limit #{pageNum},#{size}")
    List<QuestionUser> findSelectAll(@Param("selsect")String selsect,@Param("pageNum")Integer pageNum, @Param("size")Integer size);

    @Select("SELECT * FROM question where id=#{questionId}")
    QuestionUser findQuestionDetailsId(@Param("questionId") Integer questionId);

    @Update("update question set title=#{title},description=#{description},tag=#{tag} where id=#{id}")
    void update(Question question);
}