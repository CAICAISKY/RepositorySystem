package com.repository.core.repository;

import com.repository.core.enity.ProfitDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.List;

/**
 * 收益详情对象Repository
 * @author schuyler
 */
public interface ProfitDetailRepository extends JpaRepository<ProfitDetail, Integer> {
    //List<ProfitDetail> findByProfitId(Integer profitId);

    //List<ProfitDetail> findCurrentProfitDetailList(Pageable pageable);

    List<ProfitDetail> findAllByProfitId(Integer profitId);


}
