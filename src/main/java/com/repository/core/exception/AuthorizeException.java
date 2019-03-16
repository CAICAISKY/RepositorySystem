package com.repository.core.exception;

import com.repository.core.enums.ResultEnum;
import lombok.Data;

/**
 * 权限异常类
 * @author schuyler
 */
@Data
public class AuthorizeException extends RuntimeException{

    private Integer code;


    public AuthorizeException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public AuthorizeException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }
}
