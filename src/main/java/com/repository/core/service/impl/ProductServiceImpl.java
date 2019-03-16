package com.repository.core.service.impl;

import com.repository.core.config.ProjectConfig;
import com.repository.core.enity.ProductInfo;
import com.repository.core.enity.ProviderInfo;
import com.repository.core.enums.ResultEnum;
import com.repository.core.exception.ProductException;
import com.repository.core.form.ProductSaleForm;
import com.repository.core.form.StockForm;
import com.repository.core.repository.ProductInfoRepository;
import com.repository.core.repository.ProviderInfoRepository;
import com.repository.core.service.ProductService;
import com.repository.core.utils.FileUtil;
import com.repository.core.utils.RepositoryUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


/**
 * 产品Service实现类
 * @author schuyler
 */
@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductInfoRepository productRepository;
    @Autowired
    private ProviderInfoRepository providerRepository;
    @Autowired
    private ProjectConfig projectConfig;


    /**
     * 根据产品编号查询产品
     * @param productId
     * @return
     */
    @Override
    public ProductInfo findOne(Integer productId) {
        return productRepository.findById(productId).orElse(null);
    }

    /**
     * 查询产品列表
     * @param pageable
     * @return
     */
    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        Page<ProductInfo> productInfoPage = productRepository.findAll(pageable);
        return productInfoPage;
    }

    /**
     * 新增/修改产品
     * @param productInfo
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public synchronized ProductInfo save(ProductInfo productInfo, MultipartFile multipartFile) {
        //判断产品所对应的厂家在不在
        ProviderInfo providerInfo = providerRepository.findById(productInfo.getProviderId()).orElse(null);
        if (providerInfo == null) {
            log.error("【新增/修改产品】{}, providerId={}", ResultEnum.PROVIDER_NOT_EXIST.getMessage(), productInfo.getProviderId());
            throw new ProductException(ResultEnum.PROVIDER_NOT_EXIST);
        }

        //进行文件上传
        if (multipartFile != null && !StringUtils.isEmpty(multipartFile.getOriginalFilename())) {
            String fileName = FileUtil.fileUpLoad(multipartFile, projectConfig);
            productInfo.setProductIcon(fileName);
        }
        return productRepository.save(productInfo);
    }

    /**
     * 删除产品
     * @param productId
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public synchronized void delete(Integer productId) {
        ProductInfo productInfo = findOne(productId);
        //1.判断产品是否存在
        if (productInfo == null) {
            throw new ProductException(ResultEnum.PRODUCT_NOT_EXIST);
        }

        //2.判断该产品是否有库存
        if (productInfo.getProductStock() > 0) {
            log.error("【删除产品】{}, 库存={}", ResultEnum.PRODUCT_DELETE_STOCK_ERROR.getMessage(), productInfo.getProductStock());
            throw new ProductException(ResultEnum.PRODUCT_DELETE_STOCK_ERROR);
        }

        //3.判断该产品是否有在售
        if (productInfo.getOnSaleCount() > 0) {
            log.error("【删除产品】{}, 在售数={}", ResultEnum.PRODUCT_DELETE_ONSALE_ERROR.getMessage(), productInfo.getOnSaleCount());
            throw new ProductException(ResultEnum.PRODUCT_DELETE_ONSALE_ERROR);
        }

        //4.如果有图片,删除图片
        if (!StringUtils.isEmpty(productInfo.getProductIcon())) {
            FileUtil.fileDelete(productInfo.getProductIcon(), projectConfig);
        }


        productRepository.delete(productInfo);
    }

    /**
     * 通过厂家编号查找产品列表
     * @param pageable
     * @param providerId
     * @return
     */
    @Override
    public Page<ProductInfo> findAllByProviderId(Pageable pageable, Integer providerId) {
        ProductInfo queryInfo = new ProductInfo();
        queryInfo.setProviderId(providerId);
        queryInfo.setGroupId(1);
        Example<ProductInfo> example = RepositoryUtil.getExactExample(queryInfo);
        return productRepository.findAll(example, pageable);
    }

    /**
     * 根据条件查找产品
     * @param pageable
     * @param queryInfo
     * @return
     */
    @Override
    public Page<ProductInfo> findAllByConditions(Pageable pageable, ProductInfo queryInfo) {
        Example<ProductInfo> example = RepositoryUtil.getExactExample(queryInfo);
        return productRepository.findAll(example, pageable);
    }

    /**
     * 卖出产品
     * @param productSaleForm
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public synchronized void sale(ProductSaleForm productSaleForm) {


        //1.对比产品在架数量，卖出数量不能高于在架数量
        ProductInfo productInfo = findOne(productSaleForm.getProductId());
        if (productInfo == null) {
            log.error("【卖出产品】{}", ResultEnum.PRODUCT_NOT_EXIST.getMessage());
            throw new ProductException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        Integer saleCount = productInfo.getSaleCount();
        Integer onSaleCount = productInfo.getOnSaleCount();
        Integer number = productSaleForm.getNumber();

        if (onSaleCount < number) {
            log.error("【卖出产品】{}", ResultEnum.PRODUCT_SALE_OVER_ERROR.getMessage());
            throw new ProductException(ResultEnum.PRODUCT_SALE_OVER_ERROR);
        }

        //2.增加产品本月卖出数量，减少对应的在架数量
        productInfo.setOnSaleCount(onSaleCount - number);
        productInfo.setSaleCount(saleCount + number);
        save(productInfo, null);
    }


    /**
     * 进库
     * @param stockForm
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public synchronized void stockEnter(StockForm stockForm) {
        //1.查询产品
        ProductInfo productInfo = findOne(stockForm.getProductId());
        if (productInfo == null) {
            log.error("【产品进库】{}", ResultEnum.PRODUCT_NOT_EXIST.getMessage());
            throw new ProductException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        //2.增加库存
        Integer productStock = productInfo.getProductStock();
        productInfo.setProductStock(productStock + stockForm.getNumber());
        productRepository.save(productInfo);
    }

    /**
     * 产品出库
     * @param stockForm
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public synchronized void stockOut(StockForm stockForm) {
        //1.查询产品
        ProductInfo productInfo = findOne(stockForm.getProductId());
        if (productInfo == null) {
            log.error("【产品进库】{}", ResultEnum.PRODUCT_NOT_EXIST.getMessage());
            throw new ProductException(ResultEnum.PRODUCT_NOT_EXIST);
        }

        //2.判断库存数量
        Integer productStock = productInfo.getProductStock();
        Integer number = stockForm.getNumber();
        if (number > productStock) {
            log.error("【产品出库】{}", ResultEnum.STOCK_OUT_OVER_ERROR.getMessage());
            throw new ProductException(ResultEnum.STOCK_OUT_OVER_ERROR);
        }

        //3.减少库存
        productInfo.setProductStock(productStock - number);

        //4.增加在售件数
        Integer onSaleCount = productInfo.getOnSaleCount();
        productInfo.setOnSaleCount(onSaleCount + number);

        productRepository.save(productInfo);
    }


}
