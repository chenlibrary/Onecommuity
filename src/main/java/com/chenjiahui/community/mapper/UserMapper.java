package com.chenjiahui.community.mapper;

import com.chenjiahui.community.Model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Insert("insert into user(accont_id,name,token,gmt_create,gmt_modified) values (#{accoutId},#{name},#{token},#{gmt_create},#{gmt_modified})")
    void insert(User user);

    @Select("select * from user where token=#{token}")
    User findUserToken(@Param("token") String token);
}
