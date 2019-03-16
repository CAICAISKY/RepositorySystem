package com.repository.core.repository;

import com.repository.core.enity.ProfitInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


/**
 * 收益信息Repository
 * @author schuyler
 */
public interface ProfitInfoRepository extends JpaRepository<ProfitInfo, Integer> {

    /***
     * 按创建时间查询
     * @param pageable
     * @return
     */
    Page<ProfitInfo> findAllByOrderByUpdateTimeDesc(Pageable pageable);

    /***
     * 查询当月的收益总结记录
     * @param groupId
     * @return
     */
    @Query(value = "select * from profit_info where group_id = :groupId " +
            "and left(create_time,7) = left(current_date(),7) ",
            nativeQuery = true)
    ProfitInfo findByCurrentMonth(@Param("groupId")Integer groupId);
}
