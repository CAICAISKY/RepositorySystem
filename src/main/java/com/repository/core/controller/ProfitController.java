package com.repository.core.controller;

import com.repository.core.constants.URLConstant;
import com.repository.core.enity.ProfitDetail;
import com.repository.core.enity.ProfitInfo;
import com.repository.core.service.ProfitService;
import com.repository.core.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 收益控制层
 * @author schuyler
 */
@Controller
@Slf4j
@RequestMapping("/profit")
public class ProfitController {

    @Autowired
    private ProfitService profitService;

    /**
     * 收益列表
     * @param page
     * @param size
     * @param map
     * @return
     */
    @RequestMapping("/list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "10") Integer size,
                             Map<String, Object> map) {

        PageRequest pageRequest = PageRequest.of(page - 1, size);
        Page<ProfitInfo> profitInfoPage = profitService.findAllProfit(pageRequest);

        //封装
        map.put("profitInfoList", profitInfoPage.getContent());
        map.put("currentPage", profitInfoPage.getNumber() + 1);
        map.put("totalPages", profitInfoPage.getTotalPages());
        //分页的跳转链接
        map.put("pageUrl", URLConstant.PROFIT_LIST_URL);

        return new ModelAndView(URLConstant.PROFIT_LIST_MODEL, map);

    }

    @RequestMapping("/sum")
    public ModelAndView sum(HttpSession session,
                            Map<String, Object> map) {
        UserVO userVO = (UserVO) session.getAttribute("userVO");

        //进行结算
        try {
            profitService.profitSum(userVO.getGroupId());
        } catch(Exception e) {
            log.error("【盈利结算】出现异常，{}", e.getMessage());
            map.put("msg", e.getMessage());
            map.put("url", URLConstant.PROFIT_LIST_URL);
            return new ModelAndView(URLConstant.ERROR_URL_MODEL, map);
        }

        //返回结算列表
        return new ModelAndView("redirect:".concat(URLConstant.PROFIT_LIST_URL));
    }

    @GetMapping("/details")
    public ModelAndView detailes(@RequestParam(value = "page", defaultValue = "1")Integer page,
                                 @RequestParam(value = "size", defaultValue = "10")Integer size,
                                 @RequestParam("profitId")Integer profitId,
                                Map<String, Object> map) {
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        ProfitDetail profitDetail = new ProfitDetail();
        profitDetail.setProfitId(profitId);
        Page<ProfitDetail> profitDetailPage = profitService.findDetails(pageRequest, profitDetail);

        //封装
        map.put("profitDetailList", profitDetailPage.getContent());
        map.put("currentPage", profitDetailPage.getNumber() + 1);
        map.put("totalPages", profitDetailPage.getTotalPages());
        //分页的跳转链接
        map.put("pageUrl", URLConstant.PROFIT_DETAIL_LIST_URL);

        return new ModelAndView(URLConstant.PROFIT_DETAIL_LIST_MODEL, map);

    }
}
