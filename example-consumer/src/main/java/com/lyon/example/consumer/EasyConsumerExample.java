package com.lyon.example.consumer;

import com.lyon.example.common.model.User;
import com.lyon.example.common.service.UserService;
import com.lyon.lyonrpc.proxy.ServiceProxy;
import com.lyon.lyonrpc.proxy.ServiceProxyFactory;

public class EasyConsumerExample {
    public static void main(String[] args) {
        UserService userService = ServiceProxyFactory.getProxy(UserService.class);
        }
    }
