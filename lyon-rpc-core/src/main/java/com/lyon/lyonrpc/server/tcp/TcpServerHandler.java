package com.lyon.lyonrpc.server.tcp;

import com.lyon.lyonrpc.model.RpcRequest;
import com.lyon.lyonrpc.model.RpcResponse;
import com.lyon.lyonrpc.protocol.ProtocolMessage;
import com.lyon.lyonrpc.protocol.ProtocolMessageDecoder;
import com.lyon.lyonrpc.protocol.ProtocolMessageEncoder;
import com.lyon.lyonrpc.protocol.ProtocolMessageTypeEnum;
import com.lyon.lyonrpc.registry.LocalRegistry;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetSocket;

import java.io.IOException;
import java.lang.reflect.Method;

/**
 * TCP 服务器处理程序
 */
public class TcpServerHandler implements Handler<NetSocket> {
    @Override
    public void handle(NetSocket socket) {
        TcpBufferHandleWrapper bufferHandleWrapper =
                new TcpBufferHandleWrapper(buffer -> {
                    //处理请求代码
                });
        socket.handler(bufferHandleWrapper);
//        // 处理请求代码
//        netSocket.handler(buffer -> {
//            // 接受请求，解码
//            ProtocolMessage<RpcRequest> protocolMessage;
//            try {
//                protocolMessage =
//                        (ProtocolMessage<RpcRequest>) ProtocolMessageDecoder.decode(buffer);
//            } catch (IOException e) {
//                throw new RuntimeException("协议消息解码错误");
//            }
//            RpcRequest rpcRequest = protocolMessage.getBody();
//            //处理请求
//            //构造响应对象
//            RpcResponse rpcResponse = new RpcResponse();
//            try {
//                // 获取要调用的服务实现类，通过反射调用
//                Class<?> implClass = LocalRegistry.get(rpcRequest.getServiceName());
//                Method method = implClass.
//                        getMethod(rpcRequest.getMethodName(), rpcRequest.getParameterTypes());
//                Object result = method.invoke(implClass.newInstance(), rpcRequest.getArgs());
//                // 封装返回结果
//                rpcResponse.setData(result);
//                rpcResponse.setDataType(method.getReturnType());
//                rpcResponse.setMessage("ok");
//            } catch (Exception e) {
//                e.printStackTrace();
//                rpcResponse.setMessage(e.getMessage());
//                rpcResponse.setException(e);
//            }
//            // 发送响应，编码
//            ProtocolMessage.Header header = protocolMessage.getHeader();
//            header.setType((byte) ProtocolMessageTypeEnum.RESPONSE.getKey());
//            ProtocolMessage<RpcResponse> responseProtocolMessage = new ProtocolMessage<>(header, rpcResponse);
//            try {
//                Buffer encode = ProtocolMessageEncoder.encode(responseProtocolMessage);
//                netSocket.write(encode);
//            } catch (IOException e) {
//                throw new RuntimeException("协议消息编码错误");
//            }
//        });
    }
}


