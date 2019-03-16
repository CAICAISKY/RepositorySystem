package com.repository.core.service;

import com.repository.core.enity.ProviderInfo;
import com.repository.core.exception.ProviderException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 厂家Service接口
 * @author schuyler
 */
public interface ProviderService {
    ProviderInfo findOne(Integer providerId);

    Page<ProviderInfo> findAll(Pageable pageable);

    Page<ProviderInfo> findAllByGroupId(Pageable pageable, Integer groupId);

    List<ProviderInfo> findAll();

    List<ProviderInfo> findAllByGroupId(Integer gourpId);

    ProviderInfo save(ProviderInfo providerInfo);

    void delete(Integer providerId) throws ProviderException;
}
