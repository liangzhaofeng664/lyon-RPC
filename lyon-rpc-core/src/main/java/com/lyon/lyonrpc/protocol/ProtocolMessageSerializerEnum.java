package com.lyon.lyonrpc.protocol;

import cn.hutool.core.util.ObjectUtil;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 枚举消息的序列化器枚举
 */
@Getter
public enum ProtocolMessageSerializerEnum {
    JDK(0,"jdk"),
    JSON(1,"json"),
    KEYO(2,"keyo"),
    HESSIAN(3,"hessian");
    private final int key;
    private final String value;
    ProtocolMessageSerializerEnum(int key,String value){
        this.key=key;
        this.value=value;
    }

    /**
     * 获取值列表
     * @return
     */
    public static List<String> getValues(){
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }

    /**
     * 根据key获取枚举
     * @param key
     * @return
     */
    public static ProtocolMessageSerializerEnum getEnumByKey(int key){
        for (ProtocolMessageSerializerEnum anEnum:ProtocolMessageSerializerEnum.values()){
            if(anEnum.key == key){
                return anEnum;
            }
        }
        return null;
    }
    /**
     * 根据value 获取枚举
     * @param value
     * @return
     */
    public static ProtocolMessageSerializerEnum getEnumByValue(String value){
        if(ObjectUtil.isEmpty(value)){
            return null;
        }
        for (ProtocolMessageSerializerEnum anEnum:ProtocolMessageSerializerEnum.values()){
            if(anEnum.value.equals(value)){
                return anEnum;
            }
        }
        return null;
    }
}
