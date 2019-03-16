package com.repository.core.form;

import lombok.Data;

import javax.validation.MessageInterpolator;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 厂家表单校验对象
 * @author schuyler
 */
@Data
public class ProviderForm {
    /** 厂家编号 */
    private Integer providerId;
    /** 厂家名称 */
    @NotEmpty(message = "厂家名称不能为空!")
    private String providerName;

    /** 厂家地址 */
    @NotEmpty(message = "厂家地址不能为空!")
    private String providerAddress;

    /** 厂家电话 */
    @NotEmpty(message = "厂家电话不能为空!")
    private String providerPhone;

    /** 厂家联络人 */
    private String providerLiaison;

    /** 公司编号 */
    @NotNull(message = "公司编号不能为空")
    private Integer groupId;
}
