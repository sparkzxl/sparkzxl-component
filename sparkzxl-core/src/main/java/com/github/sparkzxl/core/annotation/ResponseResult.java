package com.github.sparkzxl.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * description: Response结果返回注解
 *
 * @author zhouxinlei
 * @date 2020-05-24 13:40:52
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ResponseResult {

}
