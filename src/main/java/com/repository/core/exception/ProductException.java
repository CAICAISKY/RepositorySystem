package com.repository.core.exception;

import com.repository.core.enums.ResultEnum;
import lombok.Data;

/**
 * 产品相关异常累
 * @author schuyler
 */
@Data
public class ProductException extends RuntimeException {
    private Integer code;

    public ProductException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public ProductException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }
}
