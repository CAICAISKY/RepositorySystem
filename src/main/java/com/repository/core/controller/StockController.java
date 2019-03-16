package com.repository.core.controller;

import com.repository.core.config.ProjectConfig;
import com.repository.core.constants.URLConstant;
import com.repository.core.enity.ProductInfo;
import com.repository.core.enity.ProviderInfo;
import com.repository.core.enums.ResultEnum;
import com.repository.core.exception.AuthorizeException;
import com.repository.core.exception.ProductException;
import com.repository.core.form.StockForm;
import com.repository.core.service.ProductService;
import com.repository.core.service.ProviderService;
import com.repository.core.utils.VOUtil;
import com.repository.core.vo.ProviderVO;
import com.repository.core.vo.StockVO;
import com.repository.core.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 仓管控制层
 * @author schuyler
 */
@Controller
@RequestMapping("/stock")
@Slf4j
public class StockController {

    @Autowired
    private ProviderService providerService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProjectConfig projectConfig;

    /**
     * 库存列表
     * @return
     */
    @RequestMapping("/list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "10") Integer size,
                             ProductInfo queryInfo,
                             HttpSession session,
                             Map<String, Object> map) {
        UserVO userVO = (UserVO) session.getAttribute("userVO");
        if (userVO == null) {
            throw new AuthorizeException(ResultEnum.AUTHORIZE_SESSION_NOT_EXIST);
        }


        //查询全部厂家，并转换为视图对象
        List<ProviderInfo> providerInfoList = providerService.findAllByGroupId(userVO.getGroupId());
        List<ProviderVO> providerVOList = VOUtil.ObjectList2VoList(providerInfoList, ProviderVO.class);

        //封装查询条件
        queryInfo.setGroupId(userVO.getGroupId());
        //当前端传来厂家编号时查询当前用户对应厂家的产品，否则查询当前用户的所有产品
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        if (queryInfo.getProviderId() != null) {
            map.put("providerId", queryInfo.getProviderId() );
        }

        Page<ProductInfo> productInfoPage = productService.findAllByConditions(pageRequest, queryInfo);
        List<StockVO> stockVOList = VOUtil.ObjectList2VoList(productInfoPage.getContent(), StockVO.class);

        //封装必要数据，渲染前端
        map.put("providerVOList", providerVOList);
        map.put("stockVOList", stockVOList);
        map.put("currentPage", productInfoPage.getNumber() + 1);
        map.put("totalPages", productInfoPage.getTotalPages());
        //分页栏目跳转链接
        map.put("pageUrl", URLConstant.STOCK_LIST_URL);
        return new ModelAndView(URLConstant.STOCK_LIST_MODEL, map);
    }

    /**
     * 进库
     * @return
     */
    @PostMapping("/enter")
    public ModelAndView enter(@Valid StockForm stockForm,
                              BindingResult bindingResult,
                              Map<String, Object> map) {
        //1.校验
        if (bindingResult.hasErrors()) {
            log.error("【产品进库】出现异常", bindingResult.getFieldError().getDefaultMessage());
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            map.put("url", URLConstant.STOCK_LIST_URL);
            return new ModelAndView(URLConstant.ERROR_URL_MODEL, map);
        }

        //2.进库
        try {
            productService.stockEnter(stockForm);
        } catch (ProductException e) {
            map.put("msg", e.getMessage());
            map.put("url", URLConstant.STOCK_LIST_URL);
            return new ModelAndView(URLConstant.ERROR_URL_MODEL);
        }
        return new ModelAndView("redirect:".concat(URLConstant.STOCK_LIST_URL));
    }

    /**
     * 出库
     * @return
     */
    @PostMapping("/out")
    public ModelAndView out(@Valid StockForm stockForm,
                            BindingResult bindingResult,
                            Map<String, Object> map) {
        //1.校验
        if (bindingResult.hasErrors()) {
            log.error("【产品出库】出现异常", bindingResult.getFieldError().getDefaultMessage());
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            map.put("url", URLConstant.STOCK_LIST_URL);
            return new ModelAndView(URLConstant.ERROR_URL_MODEL, map);
        }

        //2.出库
        try {
            productService.stockOut(stockForm);
        } catch (ProductException e) {
            map.put("msg", e.getMessage());
            map.put("url", URLConstant.STOCK_LIST_URL);
            return new ModelAndView(URLConstant.ERROR_URL_MODEL);
        }
        return new ModelAndView("redirect:".concat(URLConstant.STOCK_LIST_URL));
    }
}
