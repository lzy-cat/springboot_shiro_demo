package com.shiro.service;

import com.shiro.mapper.UserMapper;
import com.shiro.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User queryUserByName(String name){
        return userMapper.queryUserByName(name);
    }
}
