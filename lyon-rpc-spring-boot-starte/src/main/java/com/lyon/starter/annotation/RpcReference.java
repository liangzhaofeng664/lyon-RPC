package com.lyon.starter.annotation;

import com.lyon.lyonrpc.constant.RpcConstant;
import com.lyon.lyonrpc.fault.retry.RetryStrategyKeys;
import com.lyon.lyonrpc.fault.tolerant.TolerantStrategyKeys;
import com.lyon.lyonrpc.loadbalancer.LoadBalancerKeys;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 服务消费者注解(用于注入服务)
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface RpcReference {
    /**
     * 接口服务类
     */
    Class<?> interfaceClass() default  void.class;
    /**
     * 版本
     */
    String serviceVersion() default RpcConstant.DEFAULT_SERVICE_VERSION;
    /**
     * 负载均衡器
     */
    String loadBalancer() default LoadBalancerKeys.ROUND_ROBIN;
    /**
     * 重试策略
     */
    String retryStrategy() default RetryStrategyKeys.No;

    /**
     * 容错策略
     */
    String tolerantStrategy() default TolerantStrategyKeys.FAIL_FAST;

    /**
     * 模拟调用
     */
    boolean mock() default  false;
}
