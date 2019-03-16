package com.repository.core.repository;

import com.repository.core.enity.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 产品信息Repository
 * @author schuyler
 */
public interface ProductInfoRepository extends JpaRepository<ProductInfo, Integer> {
    List<ProductInfo> findAllByProviderId(Integer providerId);

    /**
     * 查询所有有销量的产品
     * @param groupId
     * @return
     */
    @Query("select productInfo from ProductInfo productInfo where saleCount > 0 and groupId = :groupId")
    List<ProductInfo> findSoldProductList(@Param("groupId")Integer groupId);

    @Modifying
    @Query("update ProductInfo set saleCount = 0 where groupId = :groupId")
    void clearSaleCount(@Param("groupId")Integer groupId);

}
