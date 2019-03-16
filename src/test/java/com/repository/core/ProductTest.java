package com.repository.core;


import com.repository.core.enity.ProductInfo;
import com.repository.core.service.ProductService;
import com.repository.core.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ProductTest {
    @Autowired
    private ProductService productService;

    @Test
    public void testFindAllByProviderId() {
        PageRequest pageRequest = PageRequest.of(0, 5);
        Page<ProductInfo> productInfoPage = productService.findAllByProviderId(pageRequest, 3);
        String s = JsonUtil.object2Json(productInfoPage.getContent());
        log.error("【查询测试】{}", s);
    }

    @Test
    public void testFindAll() {
        PageRequest pageRequest = PageRequest.of(0, 1);
        Page<ProductInfo> productInfoPage = productService.findAll(pageRequest);
        log.info("【查询产品列表】{}", JsonUtil.object2Json(productInfoPage.getContent()));
        Assert.assertTrue(productInfoPage.getContent().size() > 0);
    }

    @Test
    public void testSave() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProviderId(4);
        productInfo.setProductName("试一下");
        productInfo.setProductNo("okk-0");
        productInfo.setProductCode("123333");
        productInfo.setProductPrice(new BigDecimal(3.4));
        productInfo.setRetailPrice(new BigDecimal(6.8));
        ProductInfo save = productService.save(productInfo, null);
        log.info("【保存产品】{}", save);
        Assert.assertTrue(save != null);
    }

    @Test
    public void testDelete() {
        productService.delete(1);
    }

}
