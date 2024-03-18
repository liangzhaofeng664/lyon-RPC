package com.lyon.example.provider;

import com.lyon.example.common.model.User;
import com.lyon.example.common.service.UserService;


public class UserServiceImpl implements UserService {
    public User getUser(User user) {
        System.out.println("用户名: "+ user.getName());
        return user;
    }
}
