package com.lyon.lyonrpc.server;

import io.vertx.core.Vertx;

/**
 * Vert HTTP 服务器
 */
public class VertxHttpServer implements HttpServer{
    /**
     * 启动服务器
     * @param port
     */
    @Override
    public void doStart(int port) {
        //创建Vert.x实例
        Vertx vertx = Vertx.vertx();
        //创建Http服务器
        io.vertx.core.http.HttpServer server = vertx.createHttpServer();
        //监听端口并处理请求
        server.requestHandler(new HttpServerHandler());
        //启动Http服务器并监听指定端口
        server.listen(port,result->{
            if(result.succeeded()){
                System.out.println("我的名字是lyon,使用的端口:"+port);
            }else{
                System.out.println("Failed to start server:"+result.cause());
            }
        });
    }
}
