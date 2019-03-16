package com.repository.core.enity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 盈利统计实体类
 * @author schuyler
 */
@Entity
@Data
@DynamicUpdate
@DynamicInsert
@Table(indexes = @Index(name = "idx_profit_id", columnList = "profitId"))
public class ProfitInfo {
    /** 盈利编号 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer profitId;
    /** 公司编号 */
    @Column(columnDefinition = "int not null")
    private Integer groupId;
    /** 进货成本 */
    @Column(columnDefinition = "decimal(10,2) not null")
    private BigDecimal costPrice = new BigDecimal(0);
    /** 销售金额 */
    @Column(columnDefinition = "decimal(10,2) not null")
    private BigDecimal saleAmount = new BigDecimal(0);
    /** 盈利金额 */
    @Column(columnDefinition = "decimal(10,2) not null")
    private BigDecimal profit = new BigDecimal(0);
    /** 创建时间 */
    @Column(columnDefinition = "timestamp default current_timestamp")
    private Date createTime;
    /** 更新时间 */
    @Column(columnDefinition = "timestamp default current_timestamp on update current_timestamp")
    private Date updateTime;
}
