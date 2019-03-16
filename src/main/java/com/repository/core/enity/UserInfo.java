package com.repository.core.enity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * 用户信息表
 * @author schuyler
 */
@Data
@Entity
@DynamicInsert
@DynamicUpdate
public class UserInfo {

    /** 用户id */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    /** 公司编号 */
    @Column(nullable = false)
    private Integer groupId;

    /** 用户名称 */
    @Column(columnDefinition = "varchar(32) not null", unique = true)
    private String userName;

    @Column(columnDefinition = "varchar(32) not null")
    private String password;

    /** 用户角色 */
    @Column(columnDefinition = "varchar(32) not null")
    private String userRole;

    /** 创建时间 */
    @Column(columnDefinition = "timestamp default current_timestamp")
    private Date createTime;
    /** 更新时间 */
    @Column(columnDefinition = "timestamp default current_timestamp on update current_timestamp")
    private Date updateTime;
}

