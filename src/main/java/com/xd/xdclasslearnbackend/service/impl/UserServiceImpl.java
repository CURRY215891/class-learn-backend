package com.xd.xdclasslearnbackend.service.impl;

import com.xd.xdclasslearnbackend.entity.User;
import com.xd.xdclasslearnbackend.mapper.UserMapper;
import com.xd.xdclasslearnbackend.service.UserService;
import com.xd.xdclasslearnbackend.util.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 康志阳
 * @date 2026/2/20 16:43
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User login(String username, String password) {
       User user = userMapper.findByUsername(username);
       if(user != null){
           /*比较加盐加密后的密码*/
           if(Md5Util.verifyPassword(password,user.getPassword())){
               return user;
           }
       }
        return null;
    }

    @Override
    public User getUserByUsername(String username) {
        return userMapper.findByUsername(username);
    }
}
