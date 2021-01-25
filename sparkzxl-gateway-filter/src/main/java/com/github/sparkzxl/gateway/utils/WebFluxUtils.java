package com.github.sparkzxl.gateway.utils;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.MultiValueMap;

import java.util.List;

/**
 * description: WebFluxUtils工具类
 *
 * @author: zhouxinlei
 * @date: 2020-08-02 18:20:06
 */
public class WebFluxUtils {

    public static String getHeader(String headerName, ServerHttpRequest request) {
        HttpHeaders headers = request.getHeaders();
        String token = StrUtil.EMPTY;
        if (headers.isEmpty()) {
            return token;
        }

        List<String> headerList = headers.get(headerName);
        if (headerList == null || headerList.isEmpty()) {
            return token;
        }
        token = headerList.get(0);

        if (StringUtils.isNotBlank(token)) {
            return token;
        }
        MultiValueMap<String, String> queryParams = request.getQueryParams();
        if (queryParams.isEmpty()) {
            return token;
        }
        return queryParams.getFirst(headerName);
    }

    public static void addHeader(ServerHttpRequest.Builder mutate, String name, Object value) {
        if (ObjectUtil.isEmpty(value)) {
            return;
        }
        String valueStr = value.toString();
        String valueEncode = URLUtil.encode(valueStr);
        mutate.header(name, valueEncode);
    }

}
