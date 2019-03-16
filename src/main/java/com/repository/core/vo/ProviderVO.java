package com.repository.core.vo;

import lombok.Data;

/**
 * 厂家前端视图展示类
 * @author schuyler
 */
@Data
public class ProviderVO implements VO {
    /** 厂家编号 */
    private Integer providerId;

    /** 厂家名称 */
    private String providerName;
}
