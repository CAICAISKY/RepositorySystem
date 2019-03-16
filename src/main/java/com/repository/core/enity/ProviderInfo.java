package com.repository.core.enity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * 厂家信息表实体类
 * @author schuyler
 */
@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(indexes = @Index(name = "idx_provider_id", columnList = "providerId"))
public class ProviderInfo {
    /** 厂家编号 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer providerId;
    /** 公司编号 */
    @Column(nullable = false)
    private Integer groupId;
    /** 厂家名称 */
    @Column(columnDefinition = "varchar(32) not null")
    private String providerName;
    /** 厂家地址 */
    @Column(columnDefinition = "varchar(128)")
    private String providerAddress;
    /** 厂家电话 */
    @Column(columnDefinition = "varchar(16)")
    private String providerPhone;
    /** 厂家联络人 */
    @Column(columnDefinition = "varchar(32)")
    private String providerLiaison;
    /** 创建时间 */
    @Column(columnDefinition = "timestamp default current_timestamp")
    private Date createTime;
    /** 更新时间 */
    @Column(columnDefinition = "timestamp default current_timestamp on update current_timestamp")
    private Date updateTime;
}
