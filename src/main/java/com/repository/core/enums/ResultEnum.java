package com.repository.core.enums;

import lombok.Getter;

/**
 * 操作结果枚举
 * @author schuyler
 */
@Getter
public enum ResultEnum {
    PROVIDER_ADD_SUCCESS(101, "厂家新增成功"),
    PROVIDER_UPDATE_SUCCESS(102, "厂家更新成功"),
    USER_LOGOUT_SUCCESS(103, "退出成功"),
    PROVIDER_NOT_EXIST(201, "厂家不存在"),
    PRODUCT_NOT_EXIST(202, "产品不存在"),
    PRODUCT_DELETE_STOCK_ERROR(203, "删除产品异常,产品有库存!"),
    PRODUCT_DELETE_ONSALE_ERROR(204, "删除产品异常,产品有在售!"),
    PROVIDER_DELETE_HAS_PRODUCTS_ERROR(205, "删除厂家异常，该厂家存在产品!"),
    USER_LOGIN_ERROR(206, "登录异常，登录信息不正确!"),
    PRODUCT_SALE_OVER_ERROR(207, "卖出数量高于在架数量！"),
    STOCK_OUT_OVER_ERROR(208, "出库异常，出库数量大于在库数量!"),
    AUTHORIZE_COOKIE_NOT_EXIST(209, "cookie不存在!"),
    AUTHORIZE_REDIS_NOT_EXIST(210, "redis不存在!"),
    AUTHORIZE_SESSION_NOT_EXIST(211, "session不存在用户信息")
    ;

    private Integer code;
    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
