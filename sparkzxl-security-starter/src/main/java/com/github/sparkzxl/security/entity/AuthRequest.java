package com.github.sparkzxl.security.entity;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * description: 登录请求
 *
 * @author zhouxinlei
 */
@Data
public class AuthRequest {

    @NotEmpty(message = "账户不能为空")
    private String account;

    @NotEmpty(message = "密码不能为空")
    private String password;

    /**
     * 验证码key
     */
    private String captchaKey;

    /**
     * 验证码
     */
    private String captchaCode;

}
