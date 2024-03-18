package com.lyon.example.consumer;

import com.lyon.example.common.model.User;
import com.lyon.example.common.service.UserService;

import com.lyon.lyonrpc.bootstrap.ConsumerBootstrap;
import com.lyon.lyonrpc.proxy.ServiceProxyFactory;


/**
 * 简易服务消费者示例
 */
public class ConsumerExample {
    public static void main(String[] args) {
        ConsumerBootstrap.init();
        // 获取代理
        UserService userService = ServiceProxyFactory.getProxy(UserService.class);
        User user = new User();
        user.setName("lyon");

        // 调用
        User newUser = userService.getUser(user);
        if (newUser != null) {
            System.out.println(newUser.getName());
        } else {
            System.out.println("user == null");
        }
    }
}
