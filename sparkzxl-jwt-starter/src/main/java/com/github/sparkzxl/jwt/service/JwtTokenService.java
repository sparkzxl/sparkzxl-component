package com.github.sparkzxl.jwt.service;

import com.github.sparkzxl.jwt.entity.JwtUserInfo;
import com.nimbusds.jose.jwk.RSAKey;

import java.text.ParseException;

/**
 * description: jwtToken 服务类
 *
 * @author: zhouxinlei
 * @date: 2020-07-14 08:02:04
 */
public interface JwtTokenService {

    /**
     * 根据RSA算法生成token
     *
     * @param jwtUserInfo 负载信息
     * @return String
     */
    <T> String createTokenByRsa(JwtUserInfo<T> jwtUserInfo);

    /**
     * 根据RSA校验token
     *
     * @param token token
     * @return PayloadDto
     */
    <T> JwtUserInfo<T> verifyTokenByRsa(String token);

    /**
     * 根据token获取信息
     *
     * @param token token
     * @return JwtUserInfo
     */
    <T> JwtUserInfo<T> getJwtUserInfo(String token) throws ParseException;

    <T> JwtUserInfo<T> getAuthJwtInfo(String token) throws ParseException;

    /**
     * 根据HMAC算法生成token
     *
     * @param jwtUserInfo 负载信息
     * @return String
     */
    <T> String createTokenByHmac(JwtUserInfo<T> jwtUserInfo);

    /**
     * 根据HMAC校验token
     *
     * @param token token
     * @return PayloadDto
     */
    <T> JwtUserInfo<T> verifyTokenByHmac(String token);

    /**
     * 获取公钥
     *
     * @return RSAKey
     */
    RSAKey getRsaPublicKey();
}
