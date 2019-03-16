package com.repository.core.vo;

import lombok.Data;


@Data
public class StockVO implements VO{

    /** 产品编号 */
    private Integer productId;
    /** 产品名称 */
    private String productName;
    /** 产品货号 */
    private String productNo;
    /** 产品条码 */
    private String productCode;
    /** 产品图片 */
    private String productIcon;
    /** 产品库存 */
    private Integer productStock;
    /** 在售件数 */
    private Integer onSaleCount;
}
