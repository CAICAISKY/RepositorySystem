package com.repository.core.repository;

import com.repository.core.enity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 用户Respository
 * @author schuyler
 */
public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {
    UserInfo findByUserName(String userName);
}
