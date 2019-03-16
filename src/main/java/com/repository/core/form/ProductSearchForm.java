package com.repository.core.form;

import lombok.Data;

/**
 * 产品搜索校验类
 * @author schuyler
 */
@Data
public class ProductSearchForm {

    private Integer providerId;

    private String productCode;

    private String productNo;
}
