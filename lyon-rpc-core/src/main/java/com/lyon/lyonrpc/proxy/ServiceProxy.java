package com.lyon.lyonrpc.proxy;

import cn.hutool.core.collection.CollUtil;
import com.lyon.lyonrpc.RpcApplication;
import com.lyon.lyonrpc.config.RpcConfig;
import com.lyon.lyonrpc.constant.RpcConstant;
import com.lyon.lyonrpc.fault.retry.RetryStrategy;
import com.lyon.lyonrpc.fault.retry.RetryStrategyFactory;
import com.lyon.lyonrpc.fault.tolerant.TolerantStrategy;
import com.lyon.lyonrpc.fault.tolerant.TolerantStrategyFactory;
import com.lyon.lyonrpc.loadbalancer.LoadBalancer;
import com.lyon.lyonrpc.loadbalancer.LoadBalancerFactory;
import com.lyon.lyonrpc.model.RpcRequest;
import com.lyon.lyonrpc.model.RpcResponse;
import com.lyon.lyonrpc.model.ServiceMetaInfo;
import com.lyon.lyonrpc.registry.Registry;
import com.lyon.lyonrpc.registry.RegistryFactory;
import com.lyon.lyonrpc.serializer.Serializer;
import com.lyon.lyonrpc.serializer.SerializerFactory;
import com.lyon.lyonrpc.server.tcp.VertTcpClient;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 服务代理(JDK动态代理)
 */
public class ServiceProxy implements InvocationHandler {
    //指定序列化器
    final Serializer serializer
            = SerializerFactory.getInstancd(RpcApplication.getRpcConfig().getSerializer());

    /**
     * 调用代理
     *
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        String serviceName = method.getDeclaringClass().getName();
        //发请求
        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(serviceName)
                .methodName(method.getName())
                .parameterTypes(method.getParameterTypes())
                .args(args)
                .build();

        //序列化
        byte[] bodyBytes = serializer.serialize(rpcRequest);
        //从注册中心获取服务提供者请求地址
        RpcConfig rpcConfig = RpcApplication.getRpcConfig();
        Registry registry = RegistryFactory.getInstance(rpcConfig.getRegistryConfig().getRegistry());

        ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
        serviceMetaInfo.setServiceName(serviceName);
        serviceMetaInfo.setServiceVersion(RpcConstant.DEFAULT_SERVICE_VERSION);

        List<ServiceMetaInfo> serviceMetaInfoList = registry.serviceDiscover(serviceMetaInfo.getServiceKey());
        if (CollUtil.isEmpty(serviceMetaInfoList)) {
            throw new RuntimeException("暂无服务地址");
        }
        //负载均衡
        LoadBalancer loadBalancer = LoadBalancerFactory.getInstance(rpcConfig.getLoadBalancer());
        //将调用方法名(请求路径)作为负载均衡参数
        Map<String, Object> requestParams = new HashMap<>();
        requestParams.put("methodName", rpcRequest.getMethodName());
        ServiceMetaInfo selectedServiceMetaInfo = loadBalancer.select(requestParams, serviceMetaInfoList);

        //发送Rpc请求
        RpcResponse rpcResponse;
        try {
            RetryStrategy retryStrategy =
                    RetryStrategyFactory.getInstance(rpcConfig.getRetryStrategy());
            rpcResponse = retryStrategy.dpRetry(() ->
                    VertTcpClient.doRequest(rpcRequest, selectedServiceMetaInfo));
            //return rpcResponse.getData();
        } catch (Exception e) {
            //容错机制
            TolerantStrategy tolerantStrategy
                    = TolerantStrategyFactory.getInstance(rpcConfig.getToterantStrategy());
            rpcResponse = tolerantStrategy.doTolerant(null, e);
        }
        return rpcResponse.getData();
    }
}
//            Vertx vertx = Vertx.vertx();
//            NetClient netClient = vertx.createNetClient();
//            CompletableFuture<RpcResponse> responseFuture = new CompletableFuture<>();
//
//            netClient.connect(selectedServiceMetaInfo.getServicePort()
//                    , selectedServiceMetaInfo.getServiceHost(),
//                    result -> {
//                        if (result.succeeded()) {
//                            System.out.println("Connected to TCP server");
//                            io.vertx.core.net.NetSocket socket = result.result();
//
//                            //发送数据
//                            //构造消息
//                            ProtocolMessage<RpcRequest> protocolMessage = new ProtocolMessage<>();
//                            ProtocolMessage.Header header = new ProtocolMessage.Header();
//                            header.setMagic(ProtocolConstant.PROTOCOL_MAGIC);
//                            header.setVersion(ProtocolConstant.PROTOCOL_VERSION);
//                            header.setSerializer((byte) ProtocolMessageSerializerEnum.
//                                    getEnumByValue(RpcApplication.getRpcConfig().getSerializer()).getKey());
//                            header.setType((byte) ProtocolMessageTypeEnum.REQUEST.getKey());
//                            header.setRequestId(IdUtil.getSnowflakeNextId());
//                            protocolMessage.setHeader(header);
//                            protocolMessage.setBody(rpcRequest);
//                            //编码请求
//                            try {
//                                Buffer encodeBuffer = ProtocolMessageEncoder.encode(protocolMessage);
//                                socket.write(encodeBuffer);
//                            } catch (IOException e) {
//                                throw new RuntimeException("协议消息编码错误");
//                            }
//                            //接受响应
//                            socket.handler(buffer -> {
//                                try {
//                                    ProtocolMessage<RpcResponse> rpcResponseProtocolMessage = (ProtocolMessage<RpcResponse>)
//                                            ProtocolMessageDecoder.decode(buffer);
//                                    responseFuture.complete(rpcResponseProtocolMessage.getBody());
//                                } catch (IOException e) {
//                                    throw new RuntimeException("协议消息解码错误");
//                                }
//                            });
//                        } else {
//                            System.out.println("Failed to connect to TCP server");
//                        }
//                    });
//            RpcResponse rpcResponse = responseFuture.get();
//            netClient.close();
//            return rpcResponse.getData();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return null;

