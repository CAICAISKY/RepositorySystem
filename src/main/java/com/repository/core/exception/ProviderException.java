package com.repository.core.exception;

import com.repository.core.enums.ResultEnum;
import lombok.Data;

/**
 * 厂家异常类
 * @author schuyler
 */
@Data
public class ProviderException extends RuntimeException {
    private Integer code;

    public ProviderException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public ProviderException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }
}
