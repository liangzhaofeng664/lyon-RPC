package com.lyon.lyonrpc.bootstrap;

import com.lyon.lyonrpc.RpcApplication;
import com.lyon.lyonrpc.config.RegistryConfig;
import com.lyon.lyonrpc.config.RpcConfig;
import com.lyon.lyonrpc.model.ServiceMetaInfo;
import com.lyon.lyonrpc.model.ServiceRegisterInfo;
import com.lyon.lyonrpc.registry.LocalRegistry;
import com.lyon.lyonrpc.registry.Registry;
import com.lyon.lyonrpc.registry.RegistryFactory;
import com.lyon.lyonrpc.server.tcp.VertxTcpServer;

import java.util.List;

public class ProviderBootstrap {
    /**
     * 初始化
     */
    public static void init(List<ServiceRegisterInfo<?>> serviceRegisterInfoList) {
        // RPC 框架初始化（配置和注册中心）
        RpcApplication.init();
        // 全局配置
        final RpcConfig rpcConfig = RpcApplication.getRpcConfig();

        // 注册服务
        for (ServiceRegisterInfo<?> serviceRegisterInfo : serviceRegisterInfoList) {
            String serviceName = serviceRegisterInfo.getServiceName();
            // 本地注册
            LocalRegistry.register(serviceName, serviceRegisterInfo.getImplClass());

            // 注册服务到注册中心
            RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
            Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
            ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
            serviceMetaInfo.setServiceName(serviceName);
            serviceMetaInfo.setServiceHost(rpcConfig.getServerHost());
            serviceMetaInfo.setServicePort(rpcConfig.getServerPort());
            try {
                registry.register(serviceMetaInfo);
            } catch (Exception e) {
                throw new RuntimeException(serviceName + " 服务注册失败", e);
            }
        }

        // 启动服务器
        VertxTcpServer vertxTcpServer = new VertxTcpServer();
        vertxTcpServer.doStart(rpcConfig.getServerPort());


    }

}
