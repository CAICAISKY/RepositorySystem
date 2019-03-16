package com.repository.core.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 用户校验类
 * @author schuyler
 */
@Data
public class UserForm {

    @NotEmpty(message = "用户名不能为空")
    private String userName;

    @NotEmpty(message = "密码不能为空")
    private String password;
}
