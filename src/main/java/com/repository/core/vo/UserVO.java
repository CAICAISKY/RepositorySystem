package com.repository.core.vo;

import lombok.Data;

/**
 * 用户视图类
 * @author schuyler
 */
@Data
public class UserVO implements VO {
    private String userName;
    private Integer groupId;
}
