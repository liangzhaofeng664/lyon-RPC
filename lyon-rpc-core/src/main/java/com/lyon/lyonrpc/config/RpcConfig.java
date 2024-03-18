package com.lyon.lyonrpc.config;

import com.lyon.lyonrpc.fault.retry.RetryStrategyKeys;
import com.lyon.lyonrpc.fault.tolerant.TolerantStrategyKeys;
import com.lyon.lyonrpc.loadbalancer.LoadBalancerKeys;
import com.lyon.lyonrpc.serializer.SerializerKeys;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * RPC框架配置
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RpcConfig {
    /**
     * 名称
     */
    private String name = "lyon-rpc";
    /**
     * 版本号
     */
    private String version = "1.0";
    /**
     * 服务器主机名
     */
    private String serverHost =  "localhost";
    /**
     * 服务器端口号
     */
    private Integer serverPort = 8080;

    /**
     * 模拟调用
     */
    private  boolean mock = false;
    /**
     * 序列化器
     */
    private String serializer = SerializerKeys.JDK;
    /**
     * 注册表配置
     */
    private RegistryConfig registryConfig = new RegistryConfig();
    /**
     * 负载均衡器
     */
    private String loadBalancer = LoadBalancerKeys.ROUND_ROBIN;
    /**
     * 重试策略
     */
    private String retryStrategy = RetryStrategyKeys.No;
    /**
     * 容错策略
     */
    private String toterantStrategy = TolerantStrategyKeys.FAIL_FAST;

}
