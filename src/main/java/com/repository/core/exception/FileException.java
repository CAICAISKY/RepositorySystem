package com.repository.core.exception;

import com.repository.core.enums.FileEnum;
import lombok.Data;

/**
 * @author schuyler
 */
@Data
public class FileException extends RuntimeException {
    private Integer code;

    public FileException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public FileException(FileEnum fileEnum) {
        super(fileEnum.getMessage());
        this.code = fileEnum.getCode();
    }
}
