package com.lyon.lyonrpc.model;

import cn.hutool.core.util.StrUtil;
import lombok.Data;

/**
 * 服务元信息(注册信息)
 */
@Data
public class ServiceMetaInfo {
    /**
     * 服务名称
     */
    private String serviceName;
    /**
     * 服务版本号
     */
    private String serviceVersion = "1.0";
    /**
     * 服务地址
     */
    private String serviceAddress;
    /**
     * 服务分组
     */
    private String serviceGroup = "default";
    private String serviceHost;
    private int servicePort;

    /**
     * 获取服务键名
     * @return
     */
    public String getServiceKey(){
        return String.format("%s:%s",serviceName,serviceVersion);
    }
    public String getServiceNodeKey(){
        return String.format("%s:%s",getServiceKey(),serviceAddress);
    }
    /**
     * 获取完整服务地址
     *
     * @return
     */
    public String getServiceAddress() {
        if (!StrUtil.contains(serviceHost, "http")) {
            return String.format("http://%s:%s", serviceHost, servicePort);
        }
        return String.format("%s:%s", serviceHost, servicePort);
    }
}
