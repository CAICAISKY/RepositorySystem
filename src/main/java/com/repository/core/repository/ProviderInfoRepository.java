package com.repository.core.repository;

import com.repository.core.enity.ProviderInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


/**
 * 厂家Repository
 * @author schuyler
 */
public interface ProviderInfoRepository extends JpaRepository<ProviderInfo, Integer> {
    List<ProviderInfo> findAllByGroupId(Integer groupId);
}
