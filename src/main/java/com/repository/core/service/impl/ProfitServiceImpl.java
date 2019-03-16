package com.repository.core.service.impl;

import com.repository.core.enity.ProductInfo;
import com.repository.core.enity.ProfitDetail;
import com.repository.core.enity.ProfitInfo;
import com.repository.core.repository.ProductInfoRepository;
import com.repository.core.repository.ProfitDetailRepository;
import com.repository.core.repository.ProfitInfoRepository;
import com.repository.core.service.ProfitService;
import com.repository.core.utils.JsonUtil;
import com.repository.core.utils.RepositoryUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.ExceptionListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * 收益Service层实现类
 * @author schuyler
 */
@Service
@Slf4j
public class ProfitServiceImpl implements ProfitService {

    @Autowired
    private ProfitDetailRepository profitDetailRepository;

    @Autowired
    private ProfitInfoRepository profitInfoRepository;

    @Autowired
    private ProductInfoRepository productInfoRepository;

    /**
     * 查找所有盈利信息
     * @param pageable
     * @return
     */
    @Override
    public Page<ProfitInfo> findAllProfit(Pageable pageable) {
        return profitInfoRepository.findAllByOrderByUpdateTimeDesc(pageable);
    }

    /**
     * 查找所有盈利详情
     * @param pageable
     * @return
     */
    @Override
    public Page<ProfitInfo> findAllProfitDetail(Pageable pageable) {
        return null;
    }

    /**
     * 根据条件查找盈利详情
     * @param profitDetail
     * @return
     */
    @Override
    public Page<ProfitDetail> findByConditions(Pageable pageable, ProfitDetail profitDetail) {
        Example<ProfitDetail> example = RepositoryUtil.getExactExample(profitDetail);
        return profitDetailRepository.findAll(example, pageable);
    }

    /**
     * 结算
     * @param groupId
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void profitSum(Integer groupId) {
        //1.查询出本月中所有有卖出数量的产品
        List<ProductInfo> soldProductList = productInfoRepository.findSoldProductList(groupId);

        //2.计算盈利和成本,存入盈利表中
        ProfitInfo profitInfo = profitInfoRepository.findByCurrentMonth(groupId);
        if (profitInfo == null) {
            profitInfo = new ProfitInfo();
            profitInfo.setGroupId(groupId);
        }

        //--详情集合，存放计算后的数据
        List<ProfitDetail> saveList = new ArrayList<>();
        //--进行整合结算
        log.info("【盈利结算】开始进行数据处理");
        communicate(groupId, soldProductList, profitInfo, saveList);
        log.info("【盈利结算】数据处理完成，开始数据录入");

        //--插入盈利信息
        ProfitInfo result = profitInfoRepository.save(profitInfo);

        //TODO: 3.将销售记录放入销售详情与销售历史表中
        //--设置详情集合中所有详情对象的收益编号
        for (ProfitDetail profitDetail : saveList) {
            profitDetail.setProfitId(result.getProfitId());
        }

        //--盈利详情插入数据库中
        if (saveList.size() > 0) {
            profitDetailRepository.saveAll(saveList);
        }

        //4.清除产品中的所有销量
        productInfoRepository.clearSaleCount(groupId);
    }

    /**
     * 查找盈利详情
     * @param pageable
     * @param profitDetail
     * @return
     */
    @Override
    public Page<ProfitDetail> findDetails(Pageable pageable, ProfitDetail profitDetail) {

        Example<ProfitDetail> example = RepositoryUtil.getExactExample(profitDetail);
        return profitDetailRepository.findAll(example, pageable);
    }


    /**
     * 整合结算
     * @param groupId
     * @param soldProductList
     * @param profitInfo
     * @param saveList
     */
    private void communicate(Integer groupId, List<ProductInfo> soldProductList, ProfitInfo profitInfo, List<ProfitDetail> saveList) {

        List<ProfitDetail> profitDetailList = new ArrayList<>();
        Map<Integer, ProfitDetail> profitDetailMap = new HashMap<>();


        profitDetailList = profitDetailRepository.findAllByProfitId(profitInfo.getProfitId());
        profitDetailMap = profitDetailList.stream().collect(Collectors.toMap(ProfitDetail::getProductId, profitDetail -> profitDetail));


        //本月盈利总成本
        BigDecimal cost = new BigDecimal(0);
        //本月盈利总销售额
        BigDecimal saleAmount = new BigDecimal(0);

        for (ProductInfo productInfo : soldProductList) {

            ProfitDetail profitDetail = new ProfitDetail();
            Integer productId = productInfo.getProductId();

            //每个产品的卖出数量
            BigDecimal perSaleCount = new BigDecimal(productInfo.getSaleCount());
            //每个产品的总成本
            BigDecimal perCost = productInfo.getProductPrice().multiply(perSaleCount);
            //每个产品的销售金额
            BigDecimal perSaleAmount = productInfo.getRetailPrice().multiply(perSaleCount);

            //封装详情对象信息
            if (profitDetailMap.containsKey(productId)) {
                profitDetail = profitDetailMap.get(productId);
            } else {
                profitDetail.setSaleCount(0);
                profitDetail.setCostPrice(new BigDecimal(0));
                profitDetail.setSaleAmount(new BigDecimal(0));
                profitDetail.setProductId(productInfo.getProductId());
                profitDetail.setProductName(productInfo.getProductName());
                profitDetail.setProductNo(productInfo.getProductNo());
            }
            profitDetail.setSaleCount(profitDetail.getSaleCount() + productInfo.getSaleCount());
            profitDetail.setCostPrice(profitDetail.getCostPrice().add(perCost));
            profitDetail.setSaleAmount(profitDetail.getSaleAmount().add(perSaleAmount));
            profitDetail.setProfit(profitDetail.getSaleAmount().subtract(profitDetail.getCostPrice()));
            profitDetail.setGroupId(groupId);

            saveList.add(profitDetail);

            cost = cost.add(perCost);
            saleAmount = saleAmount.add(perSaleAmount);
        }
        profitInfo.setCostPrice(profitInfo.getCostPrice().add(cost));
        profitInfo.setSaleAmount(profitInfo.getSaleAmount().add(saleAmount));
        profitInfo.setProfit(profitInfo.getProfit().add(saleAmount.subtract(cost)));
    }
}
