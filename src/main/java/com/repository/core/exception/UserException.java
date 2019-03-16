package com.repository.core.exception;


import com.repository.core.enums.ResultEnum;
import lombok.Data;
import net.bytebuddy.implementation.bind.annotation.Super;

/**
 * 用户登录登出异常类
 * @author schuyler
 */
@Data
public class UserException extends RuntimeException {
    private Integer code;

    public UserException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public UserException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }
}
