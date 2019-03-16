package com.repository.core.enity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单详情实体类
 * @author schuyler
 */
@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(indexes = @Index(name = "idx_order_id", columnList = "orderId"))
public class OrderDetail {
    /** 进货详情编号 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderDetailId;
    /** 进货订单编号 */
    @Column(nullable = false)
    private Integer orderId;
    /** 产品名称 */
    @Column(columnDefinition = "varchar(32) not null")
    private String productName;
    /** 产品图片 */
    @Column(columnDefinition = "varchar(64)")
    private String productIcon;
    /** 进货价 */
    @Column(columnDefinition = "decimal(10,2) not null")
    private BigDecimal productPrice;
    /** 进货件数 */
    @Column(nullable = false)
    private Integer product_quantity;
    /** 进货金额 */
    @Column(columnDefinition = "decimal(10,2) not null")
    private BigDecimal costPrice;
    /** 创建时间 */
    @Column(columnDefinition = "timestamp default current_timestamp")
    private Date createTime;
    /** 更新时间 */
    @Column(columnDefinition = "timestamp default current_timestamp on update current_timestamp")
    private Date updateTime;
}
