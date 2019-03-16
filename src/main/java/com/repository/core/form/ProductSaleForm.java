package com.repository.core.form;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 产品卖出校验
 * @author schuyler
 */
@Data
public class ProductSaleForm {
    @NotNull(message = "产品编号不能为空")
    private Integer productId;

    @Min(value = 0, message = "卖出件数不能小于0件")
    private Integer number;
}
