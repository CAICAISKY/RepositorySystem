package com.repository.core.controller;

import com.repository.core.config.ProjectConfig;
import com.repository.core.constants.URLConstant;
import com.repository.core.enity.ProductInfo;
import com.repository.core.enity.ProviderInfo;
import com.repository.core.enums.ResultEnum;
import com.repository.core.exception.AuthorizeException;
import com.repository.core.exception.ProductException;
import com.repository.core.form.ProductForm;
import com.repository.core.form.ProductSaleForm;
import com.repository.core.service.ProductService;
import com.repository.core.service.ProviderService;
import com.repository.core.utils.VOUtil;
import com.repository.core.vo.ProviderVO;
import com.repository.core.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 产品控制层
 * @author schuyler
 */
@Slf4j
@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProviderService providerService;

    @Autowired
    private ProjectConfig projectConfig;

    /**
     * 全部/根据厂家查询产品列表
     * @param page
     * @param size
     * @param queryInfo
     * @param map
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

        //封装必要数据，渲染前端
        map.put("providerVOList", providerVOList);
        map.put("productInfoList", productInfoPage.getContent());
        map.put("currentPage", productInfoPage.getNumber() + 1);
        map.put("totalPages", productInfoPage.getTotalPages());
        //分页栏目跳转链接
        map.put("pageUrl", URLConstant.PRODUCT_LIST_URL);
        return new ModelAndView(URLConstant.PRODUCT_LIST_MODEL, map);
    }

    /**
     * 新增/修改产品页面跳转
     * @param productId
     * @param map
     * @return
     */
    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "productId", required = false) Integer productId,
                              HttpSession session,
                              Map<String, Object> map) {
        //查询当前用户的所有厂家，作为下拉选择
        UserVO userVO = (UserVO) session.getAttribute("userVO");
        List<ProviderInfo> providerInfoList = providerService.findAllByGroupId(userVO.getGroupId());
        List<ProviderVO> providerVOList = VOUtil.ObjectList2VoList(providerInfoList, ProviderVO.class);

        ProductInfo productInfo = new ProductInfo();
        if (productId != null) {
            productInfo = productService.findOne(productId);
        }
        map.put("providerVOList", providerVOList);
        map.put("productInfo", productInfo);
        map.put("pageUrl", URLConstant.PRODUCT_LIST_URL);
        return new ModelAndView(URLConstant.PRODUCT_INDEX_MODEL, map);
    }

    @PostMapping("/save")
    public ModelAndView save(@Valid ProductForm productForm,
                             BindingResult bindingResult,
                             @RequestParam(value = "file", required = false) MultipartFile multipartFile,
                             Map<String, Object> map) {
        if (bindingResult.hasErrors()) {
            log.error("【新增/修改产品】出现异常,{}", bindingResult.getFieldError().getDefaultMessage());
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            map.put("url", URLConstant.PRODUCT_LIST_URL);
            return new ModelAndView(URLConstant.ERROR_URL_MODEL, map);
        }

        //1.判断是新增还是修改
        ProductInfo productInfo = new ProductInfo();
        if (productForm.getProductId() != null) {
            productInfo = productService.findOne(productForm.getProductId());
        }

        BeanUtils.copyProperties(productForm, productInfo);

        try {
            productService.save(productInfo, multipartFile);
        } catch (Exception e) {
            map.put("msg", e.getMessage());
            map.put("url", URLConstant.PRODUCT_LIST_URL);
            return new ModelAndView(URLConstant.ERROR_URL_MODEL, map);
        }

        return new ModelAndView("redirect:".concat(URLConstant.PRODUCT_LIST_URL));
    }

    /**
     * 删除产品
     * @param productId
     * @param map
     * @return
     */
    @GetMapping("/delete")
    public ModelAndView delete(@RequestParam("productId") Integer productId,
                               @RequestParam(value = "providerId", required = false) Integer providerId,
                               Map<String, Object> map) {
        try {
            productService.delete(productId);
        } catch (ProductException e) {
            //跳转到错误页面
            map.put("msg", e.getMessage());
            map.put("url", URLConstant.PRODUCT_LIST_URL);
            return new ModelAndView(URLConstant.ERROR_URL_MODEL, map);
        }

        //如果删除的时候选择了厂家那么删除完后也返回选择了厂家的产品列表
        if (providerId != null) {
            map.put("providerId", providerId);
        }
        return new ModelAndView("redirect:".concat(URLConstant.PRODUCT_LIST_URL), map);
    }

    @PostMapping("/sale")
    public ModelAndView sale(@Valid ProductSaleForm productSaleForm,
                             BindingResult bindingResult,
                             Map<String, Object> map) {
        if (bindingResult.hasErrors()) {
            log.error("【卖出产品】{}", bindingResult.getFieldError().getDefaultMessage());
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            map.put("url", URLConstant.PRODUCT_LIST_URL);
            return new ModelAndView(URLConstant.ERROR_URL_MODEL, map);
        }

        try {
            productService.sale(productSaleForm);
        } catch (ProductException e) {
            map.put("msg", e.getMessage());
            map.put("url", URLConstant.PRODUCT_LIST_URL);
            return new ModelAndView(URLConstant.ERROR_URL_MODEL, map);
        }
        return new ModelAndView("redirect:".concat(URLConstant.PRODUCT_LIST_URL));
    }
}



