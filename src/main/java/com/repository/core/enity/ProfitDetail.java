package com.repository.core.enity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.stereotype.Controller;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 盈利详情实体类
 * @author schuyler
 */
@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(indexes = @Index(name = "idx_profit_id", columnList = "profitId"))
public class ProfitDetail {
    /** 盈利详情编号 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer profitDetailId;
    /** 盈利编号 */
    @Column(nullable = false)
    private Integer profitId;
    /** 产品编号 */
    @Column(nullable = false)
    private Integer productId;
    /** 公司编号 */
    @Column(columnDefinition = "int not null")
    private Integer groupId;
    /** 产品名称 */
    @Column(columnDefinition = "varchar(32) not null")
    private String productName;
    /** 产品货号 */
    @Column(columnDefinition = "varchar(32) not null")
    private String productNo;
    /** 卖出件数 */
    @Column(nullable = false)
    private Integer saleCount;
    /** 成本 */
    @Column(columnDefinition = "decimal(10,2) not null")
    private BigDecimal costPrice;
    /** 销售金额 */
    @Column(columnDefinition = "decimal(10,2) not null")
    private BigDecimal saleAmount;
    /** 利润 */
    @Column(columnDefinition = "decimal(10,2) not null")
    private BigDecimal profit;
    /** 创建时间 */
    @Column(columnDefinition = "timestamp default current_timestamp")
    private Date createTime;
    /** 更新时间 */
    @Column(columnDefinition = "timestamp default current_timestamp on update current_timestamp")
    private Date updateTime;
}
