package com.github.sparkzxl.log.entity;

import lombok.Data;

/**
 * description: 异常请求信息
 *
 * @author zhouxinlei
 */
@Data
public class RequestErrorInfo {

    /**
     * 请求IP
     */
    private String ip;
    /**
     * 请求路径
     */
    private String url;
    /**
     * http方法类型
     */
    private String httpMethod;
    /**
     * 请求类方法
     */
    private String classMethod;
    /**
     * 请求参数
     */
    private Object requestParams;

    /**
     * 错误原因
     */
    private String error;

    /**
     * exception中包含的信息
     */
    private String errorMsg;

    private String throwExceptionClass;


}
