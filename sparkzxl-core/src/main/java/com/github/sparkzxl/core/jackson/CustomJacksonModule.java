package com.github.sparkzxl.core.jackson;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.PackageVersion;
import com.github.sparkzxl.core.enums.Enumerator;
import com.github.sparkzxl.core.serializer.EnumSerializer;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * description: 基础类型自定义序列化 & 反序列化 规则
 *
 * @author zhouxinlei
 */
public class CustomJacksonModule extends SimpleModule {

    public CustomJacksonModule() {
        super(PackageVersion.VERSION);
        // 序列化基础类型
        this.addSerializer(Long.class, ToStringSerializer.instance);
        this.addSerializer(Long.TYPE, ToStringSerializer.instance);
        this.addSerializer(BigInteger.class, ToStringSerializer.instance);
        this.addSerializer(BigDecimal.class, ToStringSerializer.instance);
        this.addSerializer(Enumerator.class, EnumSerializer.INSTANCE);
    }

}
