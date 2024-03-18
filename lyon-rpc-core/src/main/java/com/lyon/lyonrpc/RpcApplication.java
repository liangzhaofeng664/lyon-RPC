package com.lyon.lyonrpc;

import com.lyon.lyonrpc.config.RegistryConfig;
import com.lyon.lyonrpc.config.RpcConfig;
import com.lyon.lyonrpc.constant.RpcConstant;
import com.lyon.lyonrpc.registry.Registry;
import com.lyon.lyonrpc.registry.RegistryFactory;
import com.lyon.lyonrpc.utilis.ConfigUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * RPC 框架应用
 * 相当于holder 存放了项目全局用到的变量 双检单例模式实现
 */
@Slf4j
public class RpcApplication {
    private static volatile RpcConfig rpcConfig;

    /**
     * 框架初始化,支持传入自定义配置
     * @param newRpcConfig
     */
    public static void init(RpcConfig newRpcConfig){
        rpcConfig = newRpcConfig;
        log.info("rpc init,config = {}",newRpcConfig.toString());
        //注册中心初始化
        RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
       Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
       registry.init(registryConfig);
       log.info("registry init,config={}",registryConfig);

       //创建并注册ShutDown Hook，JVM退出时执行操作
        Runtime.getRuntime().addShutdownHook(new Thread(registry::destory));
    }

    /**
     * 初始化
     */
    public static void init(){
        RpcConfig newRpcConfig;
        try{
            newRpcConfig = ConfigUtils.loadConfig(RpcConfig.class,RpcConstant.DEFAULT_CONFIG_PREFIX);
        }catch (Exception e){
            //配置加载失败,使用默认值
            newRpcConfig = new RpcConfig();
        }
        init(newRpcConfig);
    }

    /**
     * 获取配置
     * @return
     */
    public static RpcConfig getRpcConfig(){
        if(rpcConfig == null){
            synchronized (RpcApplication.class){
                if(rpcConfig == null){
                    init();
                }
            }
        }
        return rpcConfig;

    }
}