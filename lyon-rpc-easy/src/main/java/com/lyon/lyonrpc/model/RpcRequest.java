package com.lyon.lyonrpc.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 封装调用所需要的信息的 比如服务名称,方法名称,参数类型列表,参数列表 这些都是Java反射机制需要的参数
 */
@Data
@Builder

public class RpcRequest implements Serializable {
    /**
     * 服务名称
     */
    private String serviceName;

    /**
     * 方法名称
     */
    private String methodName;

    /**
     * 参数类型列表
     */
    private  Class<?>[] parameterTypes;

    /**
     * 参数列表
     */
    private Object[] args;


}
