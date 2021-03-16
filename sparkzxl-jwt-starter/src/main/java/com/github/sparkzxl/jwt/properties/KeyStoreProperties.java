package com.github.sparkzxl.jwt.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * description: JWT属性类
 *
 * @author zhouxinlei
 */
@Data
@ConfigurationProperties(prefix = "sparkzxl.key-store")
public class KeyStoreProperties {

    /**
     * 是否启用
     */
    private boolean enable;

    /**
     * 密钥文件路径
     */
    private String path;
    /**
     * 别名
     */
    private String alias;

    /**
     * 密钥密码
     */
    private String password;

}
