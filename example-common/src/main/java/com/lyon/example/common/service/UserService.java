package com.lyon.example.common.service;

import com.lyon.example.common.model.User;

/**
 *  用户服务
 */
public interface UserService {
    /**
     * 获取用户
     * @param user
     * @return
     */
    User getUser(User user);

    /**
     * mock模拟
     * @return
     */
    default  short getNumber(){
        return 1;
    }
}
