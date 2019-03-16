package com.repository.core.service.impl;

import com.repository.core.enity.ProductInfo;
import com.repository.core.enity.ProviderInfo;
import com.repository.core.enums.ResultEnum;
import com.repository.core.exception.ProviderException;
import com.repository.core.repository.ProductInfoRepository;
import com.repository.core.repository.ProviderInfoRepository;
import com.repository.core.service.ProductService;
import com.repository.core.service.ProviderService;
import com.repository.core.utils.RepositoryUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.xml.transform.Result;
import java.util.List;

/**
 * 厂家Servidce服务层
 * @author schuyler
 */
@Service
@Slf4j
public class ProviderServiceImpl implements ProviderService {

    @Autowired
    private ProviderInfoRepository providerRepository;

    @Autowired
    private ProductInfoRepository productRepository;

    /**
     * 根据厂家编号查找厂家
     * @param providerId 厂家编号
     * @return 厂家实体类
     */
    @Override
    public ProviderInfo findOne(Integer providerId) {
        return providerRepository.findById(providerId).orElse(null);
    }

    /**
     * 查询厂家列表
     * @param pageable 分页对象
     * @return 厂家列表分页对象
     */
    @Override
    public Page<ProviderInfo> findAll(Pageable pageable) {
        return providerRepository.findAll(pageable);
    }

    /**
     * 查询厂家列表-根据公司编号
     * @param pageable
     * @param groupId
     * @return
     */
    @Override
    public Page<ProviderInfo> findAllByGroupId(Pageable pageable, Integer groupId) {
        ProviderInfo providerInfo = new ProviderInfo();
        providerInfo.setGroupId(groupId);
        Example<ProviderInfo> example = RepositoryUtil.getExactExample(providerInfo);

        return providerRepository.findAll(example, pageable);
    }

    @Override
    public List<ProviderInfo> findAll() {
        return providerRepository.findAll();
    }

    @Override
    public List<ProviderInfo> findAllByGroupId(Integer gourpId) {
        return providerRepository.findAllByGroupId(gourpId);
    }

    /**
     * 新增/更新厂家
     * @param providerInfo 厂家对象
     * @return 厂家对象
     */
    @Override
    public ProviderInfo save(ProviderInfo providerInfo) {
        return providerRepository.save(providerInfo);
    }

    /**
     * 删除厂家
     * @param providerId 厂家编号
     */
    @Override
    public synchronized void delete(Integer providerId) {
        ProviderInfo providerInfo = findOne(providerId);
        //1.删除之前判断一下该厂家是否存在
        if (providerInfo == null) {
            log.error("【删除厂家】出现异常，{}", ResultEnum.PROVIDER_NOT_EXIST.getMessage());
            throw new ProviderException(ResultEnum.PROVIDER_NOT_EXIST);
        }
        //2.删除之前判断一下该厂家是否存在对应的产品
        List<ProductInfo> productInfoList = productRepository.findAllByProviderId(providerId);
        if (productInfoList.size() > 0) {
            log.error("【删除厂家】{}", ResultEnum.PROVIDER_DELETE_HAS_PRODUCTS_ERROR.getMessage());
            throw new ProviderException(ResultEnum.PROVIDER_DELETE_HAS_PRODUCTS_ERROR);
        }
        providerRepository.deleteById(providerId);
    }
}
