package com.repository.core.service;

import com.repository.core.enity.ProfitDetail;
import com.repository.core.enity.ProfitInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


/**
 * 收益Service接口
 * @author schuyler
 */
public interface ProfitService {
    Page<ProfitInfo> findAllProfit(Pageable pageable);

    Page<ProfitInfo> findAllProfitDetail(Pageable pageable);

    Page<ProfitDetail> findByConditions(Pageable pageable, ProfitDetail profitDetail);

    void profitSum(Integer groupId);

    Page<ProfitDetail> findDetails(Pageable pageable, ProfitDetail profitDetail);

    //Page<ProfitDetail> findCurrentDetailList(Pageable pageable);
}
