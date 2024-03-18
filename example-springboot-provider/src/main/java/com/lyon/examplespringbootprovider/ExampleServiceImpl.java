package com.lyon.examplespringbootprovider;

import com.lyon.example.common.model.User;
import com.lyon.example.common.service.UserService;
import com.lyon.starter.annotation.RpcReference;
import org.springframework.stereotype.Service;

@Service
public class ExampleServiceImpl {
    @RpcReference
    private UserService userService;
    public void test(){
        User user = new User();
        user.setName("lyon");
        User resultUser = userService.getUser(user);
        System.out.println(resultUser.getName());
    }
}