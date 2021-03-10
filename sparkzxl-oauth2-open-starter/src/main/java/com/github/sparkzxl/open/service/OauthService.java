package com.github.sparkzxl.open.service;

import com.github.sparkzxl.core.entity.CaptchaInfo;
import com.github.sparkzxl.open.entity.AccessTokenInfo;
import com.github.sparkzxl.open.entity.AuthorizationCallBackResponse;
import com.github.sparkzxl.open.entity.AuthorizationRequest;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * description: Oauth认证 服务类
 *
 * @author: zhouxinlei
 * @date: 2020-06-25 09:49:22
 */
public interface OauthService {

    /**
     * get请求授权登录
     *
     * @param principal            认证主体
     * @param authorizationRequest 认证请求
     * @return OAuth2AccessToken
     * @throws HttpRequestMethodNotSupportedException 请求方法异常
     */
    OAuth2AccessToken getAccessToken(Principal principal, AuthorizationRequest authorizationRequest) throws HttpRequestMethodNotSupportedException;

    /**
     * POST请求授权登录
     *
     * @param principal            认证主体
     * @param authorizationRequest 认证请求
     * @return OAuth2AccessToken
     * @throws HttpRequestMethodNotSupportedException 请求方法异常
     */
    OAuth2AccessToken postAccessToken(Principal principal, AuthorizationRequest authorizationRequest) throws HttpRequestMethodNotSupportedException;

    /**
     * 生成验证码
     *
     * @param type 验证码类型
     * @return CaptchaInfo
     */
    CaptchaInfo createCaptcha(String type);

    /**
     * 校验验证码
     *
     * @param key   前端上送 key
     * @param value 前端上送待校验值
     * @return 是否成功
     */
    boolean checkCaptcha(String key, String value);

    /**
     * 获取授权认证连接
     *
     * @param clientId 客户端id
     * @param frontUrl 前端地址
     * @return String
     */
    String getAuthorizeUrl(String clientId, String frontUrl);


    /**
     * 授权回调处理
     *
     * @param authorizationCode 授权码
     * @param loginState        登录态
     * @return AuthorizationCallBackResponse
     */
    AuthorizationCallBackResponse callBack(String authorizationCode, String loginState);


    /**
     * 根据token态交换token
     *
     * @param tokenState token态
     * @return AccessTokenInfo
     */
    AccessTokenInfo exchangeToken(String tokenState);

    /**
     * 自定义退出登录
     *
     * @param request HttpServletRequest
     * @return boolean
     */
    boolean logout(HttpServletRequest request);

}
