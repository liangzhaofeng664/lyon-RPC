package com.lyon.example.provider;

import com.lyon.example.common.service.UserService;
import com.lyon.lyonrpc.bootstrap.ProviderBootstrap;
import com.lyon.lyonrpc.model.ServiceRegisterInfo;

import java.util.ArrayList;
import java.util.List;

public class ProviderExample {
    public static void main(String[] args) {
        // 要注册的服务
        List<ServiceRegisterInfo<?>> serviceRegisterInfoList = new ArrayList<>();
        ServiceRegisterInfo<UserService> serviceRegisterInfo = new ServiceRegisterInfo<>(UserService.class.getName(), UserServiceImpl.class);
        serviceRegisterInfoList.add(serviceRegisterInfo);

        // 服务提供者初始化
        ProviderBootstrap.init(serviceRegisterInfoList);
    }
}
