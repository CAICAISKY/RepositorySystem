package com.repository.core;

import com.repository.core.enity.ProviderInfo;
import com.repository.core.exception.ProviderException;
import com.repository.core.service.ProviderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ProviderTest {

    @Autowired
    private ProviderService providerService;

    @Test
    public void testSave() {
        ProviderInfo providerInfo = new ProviderInfo();
        providerInfo.setProviderName("华辉");
        providerService.save(providerInfo);
    }

    @Test
    public void testFindById() {
        ProviderInfo one = providerService.findOne(1);
        log.info("one={}", one);
        Assert.assertNotNull(one);
    }

    @Test
    public void testFindAll() {
        PageRequest pageRequest = PageRequest.of(0, 5);
        Page<ProviderInfo> all = providerService.findAll(pageRequest);
        Assert.assertTrue(all.getContent().size()>0);
    }

    @Test
    public void testDelete() {
        try {
            providerService.delete(1);
        } catch (ProviderException e) {
            e.printStackTrace();
        }
    }
}
