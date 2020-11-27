package com.github.sparkzxl.core.support;

import com.github.sparkzxl.core.base.code.BaseEnumCode;
import lombok.Getter;

/**
 * description：BaseException
 *
 * @author zhouxinlei
 * @date  2020-06-04 12:40:33
 */
@Getter
public class BaseException extends RuntimeException {

    private static final long serialVersionUID = 5092096093495323869L;

    private BaseEnumCode baseEnumCode;

    private Object[] args;

    private String message;

    public BaseException(BaseEnumCode baseEnumCode) {
        this.baseEnumCode = baseEnumCode;
    }

    public BaseException(Throwable cause) {
        super(cause);
    }

    public BaseException(String message) {
        super(message);
    }

    public BaseException(BaseEnumCode baseEnumCode, Object[] args, String message) {
        this.baseEnumCode = baseEnumCode;
        this.args = args;
        this.message = message;
    }

    public BaseException(BaseEnumCode baseEnumCode, Object[] args, String message, Throwable cause) {
        this.baseEnumCode = baseEnumCode;
        this.args = args;
        this.message = message;
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}
