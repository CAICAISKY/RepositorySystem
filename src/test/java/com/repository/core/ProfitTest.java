package com.repository.core;

import com.repository.core.service.ProfitService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ProfitTest {

    @Autowired
    private ProfitService profitService;

    @Test
    public void testFindProductList() {
        //profitService.profitSum();
    }
}
