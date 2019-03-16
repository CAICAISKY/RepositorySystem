package com.repository.core.form;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 库存校验类
 * @author schuyler
 */
@Data
public class StockForm {

    @NotNull(message = "产品编号不能为空")
    private Integer productId;

    @Min(value = 0, message = "进库数量不能少于0个")
    private Integer number;
}
