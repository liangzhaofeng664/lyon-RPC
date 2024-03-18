package com.lyon.lyonrpc.loadbalancer;

import com.lyon.lyonrpc.spi.SpiLoader;

/**
 * 负载均器工厂(工厂模式,用于获取负载均衡器对象)
 */
public class LoadBalancerFactory {
    static {
        SpiLoader.load(LoadBalancer.class);
    }

    /**
     * 默认负载均衡器
     */
    private static final LoadBalancer DEFSULT_LOAD_BALANCER = new RoundRobinLoadBalancer();

    /**
     * 获取实例
     * @param key
     * @return
     */
    public static LoadBalancer getInstance(String key){
        return SpiLoader.getInstance(LoadBalancer.class,key);
    }

}
