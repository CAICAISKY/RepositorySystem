package com.repository.core.form;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 产品校验类
 * @author schuyler
 */
@Data
public class ProductForm {

    /** 产品编号 */
    private Integer productId;
    /** 厂家编号 */
    @NotNull(message = "厂家不能为空!")
    private Integer providerId;
    /** 产品名称 */
    @NotEmpty(message = "产品名称不能为空")
    private String productName;
    /** 产品货号 */
    private String productNo;
    /** 产品条码 */
    private String productCode;
    /** 产品图片 */
    private String productIcon;
    /** 产品库存 */
    private Integer productStock;
    /** 卖出件数 */
    private Integer saleCount;
    /** 在售件数 */
    private Integer onSaleCount;
    /** 进货价 */
    @DecimalMin( value = "0.1",message = "进货价不能小于0.1元")
    private BigDecimal productPrice;
    /** 零售价 */
    private BigDecimal retailPrice;
    /** 公司编号 */
    @NotNull(message = "公司编号不能为空")
    private Integer groupId;
}
