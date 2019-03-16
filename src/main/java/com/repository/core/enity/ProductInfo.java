package com.repository.core.enity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.stereotype.Controller;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 产品信息实体类
 * @author schuyler
 */
@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(indexes = @Index(name = "idx_provider_id", columnList = "providerId"))
public class ProductInfo {
    /** 产品编号 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId;
    /** 厂家编号 */
    @Column(nullable = false)
    private Integer providerId;
    /** 公司编号 */
    @Column(nullable = false)
    private Integer groupId;
    /** 产品名称 */
    @Column(columnDefinition = "varchar(32) not null")
    private String productName;
    /** 产品货号 */
    @Column(columnDefinition = "varchar(32)")
    private String productNo;
    /** 产品条码 */
    @Column(columnDefinition = "varchar(32)")
    private String productCode;
    /** 产品图片 */
    @Column(columnDefinition = "varchar(128)")
    private String productIcon;
    /** 产品库存 */
    @Column(columnDefinition = "int not null default '0'")
    private Integer productStock;
    /** 卖出件数 */
    @Column(columnDefinition = "int not null default '0'")
    private Integer saleCount;
    /** 在售件数 */
    @Column(columnDefinition = "int not null default '0'")
    private Integer onSaleCount;
    /** 进货价 */
    @Column(columnDefinition = "decimal(10,2) not null")
    private BigDecimal productPrice;
    /** 零售价 */
    @Column(columnDefinition = "decimal(10,2)")
    private BigDecimal retailPrice;
    /** 创建时间 */
    @Column(columnDefinition = "timestamp default current_timestamp")
    private Date createTime;
    /** 更新时间 */
    @Column(columnDefinition = "timestamp default current_timestamp on update current_timestamp")
    private Date updateTime;
}
