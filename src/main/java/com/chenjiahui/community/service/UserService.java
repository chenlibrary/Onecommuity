package com.chenjiahui.community.service;

import com.chenjiahui.community.Model.User;
import com.chenjiahui.community.Model.UserExample;
import com.chenjiahui.community.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public void UserifNull(User user){
        System.out.println(user.getAccontId());
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andAccontIdEqualTo(user.getAccontId());

        int num = (int)userMapper.countByExample(userExample);
        if(num==0){
            //插入
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);

        }else{
            //更新
           // user.setGmt_create(System.currentTimeMillis());
            user.setGmtModified(System.currentTimeMillis());
            System.out.println("updateUser.getId()"+user.getId());
            userMapper.updateByExampleSelective(user,userExample);
        }

    }
}
