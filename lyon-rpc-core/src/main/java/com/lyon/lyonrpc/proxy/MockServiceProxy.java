package com.lyon.lyonrpc.proxy;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
@Slf4j
/**
 * 生成mock代理服务(JDK动态代理）
 * 需要提供一个根据服务接口类型返回固定值的方法
 */
public class MockServiceProxy implements InvocationHandler {
    /**
     * 调用代理
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //根据方法的返回值类型,生成特定的默认值对象
        Class<?> methodReturnType = method.getReturnType();
        log.info("mock invoke {}",method.getName());
        return getDefaultObject(methodReturnType);
    }

    /**
     * 生成指定类型的默认对象
     * @param type
     * @return
     */
    private  Object getDefaultObject(Class<?> type){
        //基本类型
        if(type.isPrimitive()){
            if(type == boolean.class){
                return false;
            }else if(type == short.class){
                return(short)0;
            }else if(type == int.class){
                return 0;
            }else if(type == long.class){
                return 0L;
            }
        }
        //对象类型
        return null;

    }
}
