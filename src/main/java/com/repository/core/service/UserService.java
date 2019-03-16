package com.repository.core.service;

import com.repository.core.enity.UserInfo;

/**
 * 用户Service接口
 * @author schuyler
 */
public interface UserService {

    UserInfo login(String userName, String password);

    void logout();
}
