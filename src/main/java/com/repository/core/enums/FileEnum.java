package com.repository.core.enums;


import lombok.Getter;

/**
 * 文件相关枚举
 * @author schuyler
 */
@Getter
public enum FileEnum {
    FILE_DIR_MAKE_ERROR(01, "创建文件夹异常"),
    FILE_COPY_ERROR(02, "文件复制异常")
    ;


    private Integer code;
    private String message;

    FileEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
