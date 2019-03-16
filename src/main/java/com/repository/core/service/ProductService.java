package com.repository.core.service;

import com.repository.core.enity.ProductInfo;
import com.repository.core.form.ProductSaleForm;
import com.repository.core.form.StockForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

/**
 * 产品Service接口
 * @author schuyler
 */
public interface ProductService {
    ProductInfo findOne(Integer productId);
    Page<ProductInfo> findAll(Pageable pageable);
    ProductInfo save(ProductInfo productInfo, MultipartFile multipartFile);
    void delete(Integer productId);
    Page<ProductInfo> findAllByProviderId(Pageable pageable, Integer providerId);
    Page<ProductInfo> findAllByConditions(Pageable pageable, ProductInfo productInfo);
    void sale(ProductSaleForm productSaleForm);
    void stockEnter(StockForm stockForm);
    void stockOut(StockForm stockForm);
}
