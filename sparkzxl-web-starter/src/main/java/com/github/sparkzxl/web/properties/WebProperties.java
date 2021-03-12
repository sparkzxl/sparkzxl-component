package com.github.sparkzxl.web.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * description: 拦截器自定义
 *
 * @author: zhouxinlei
 * @date: 2021-03-12 11:21:41
 */
@Data
@ConfigurationProperties(prefix = "web")
public class WebProperties {

    private String interceptor = "responseResultInterceptor";
}
