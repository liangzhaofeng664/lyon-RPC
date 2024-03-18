package com.lyon.example.provider;

import com.lyon.example.common.service.UserService;
import com.lyon.lyonrpc.RpcApplication;
import com.lyon.lyonrpc.config.RegistryConfig;
import com.lyon.lyonrpc.config.RpcConfig;
import com.lyon.lyonrpc.model.ServiceMetaInfo;
import com.lyon.lyonrpc.registry.LocalRegistry;
import com.lyon.lyonrpc.registry.Registry;
import com.lyon.lyonrpc.registry.RegistryFactory;
import com.lyon.lyonrpc.server.HttpServer;
import com.lyon.lyonrpc.server.VertxHttpServer;
import com.lyon.lyonrpc.server.tcp.VertxTcpServer;


/**
 * 简易服务提供者示例
 */
public class EasyProviderExample {
    public static void main(String[] args){

        // 注册服务
        LocalRegistry.register(UserService.class.getName(), UserServiceImpl.class);

        // 启动 web 服务
        HttpServer httpServer = new VertxHttpServer();
        httpServer.doStart(9999);

    }
}
