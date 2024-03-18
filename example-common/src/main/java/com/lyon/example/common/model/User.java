package com.lyon.example.common.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户:需要实现序列化接口,为后序网络传输序列化提供支持
 */
@Data
public class User implements Serializable {
    private String name;

}
