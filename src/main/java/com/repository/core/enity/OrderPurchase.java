package com.repository.core.enity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 进货订单实体类
 * @author schuyler
 */
@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(indexes = @Index(name = "idx_order_id", columnList = "orderId"))
public class OrderPurchase {
    /** 进货订单编号 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;
    /** 操作员 */
    @Column(columnDefinition = "varchar(32) not null")
    private String orderOperator;
    /** 进货金额 */
    @Column(columnDefinition = "decimal(10,2) not null")
    private BigDecimal orderAmount;
    /** 创建时间 */
    @Column(columnDefinition = "timestamp default current_timestamp")
    private Date createTime;
    /** 更新时间 */
    @Column(columnDefinition = "timestamp default current_timestamp on update current_timestamp")
    private Date updateTime;
}
