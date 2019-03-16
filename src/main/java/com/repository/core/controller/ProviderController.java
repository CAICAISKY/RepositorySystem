package com.repository.core.controller;

import com.repository.core.config.ProjectConfig;
import com.repository.core.constants.URLConstant;
import com.repository.core.enity.ProviderInfo;
import com.repository.core.enums.ResultEnum;
import com.repository.core.exception.AuthorizeException;
import com.repository.core.exception.ProviderException;
import com.repository.core.form.ProviderForm;
import com.repository.core.service.ProviderService;
import com.repository.core.utils.JsonUtil;
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
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.xml.transform.Result;
import java.util.Map;

/**
 * 厂家控制层
 * @author schuyler
 */
@Controller
@Slf4j
@RequestMapping("/provider")
public class ProviderController {
    @Autowired
    private ProviderService providerService;

    @Autowired
    private ProjectConfig projectConfig;


    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "10") Integer size,
                             HttpSession session,
                             Map<String, Object> map) {
        UserVO userVO = (UserVO) session.getAttribute("userVO");
        if (userVO == null) {
            throw new AuthorizeException(ResultEnum.AUTHORIZE_SESSION_NOT_EXIST);
        }

        PageRequest pageRequest = PageRequest.of(page - 1, size);
        Page<ProviderInfo> providerInfoPage = providerService.findAllByGroupId(pageRequest, userVO.getGroupId());

        map.put("providerInfoList", providerInfoPage.getContent());
        map.put("currentPage", providerInfoPage.getNumber() + 1);
        map.put("totalPages", providerInfoPage.getTotalPages());
        //分页的跳转链接
        map.put("pageUrl", URLConstant.PROVIDER_LIST_URL);
        return new ModelAndView(URLConstant.PROVIDER_LIST_MODEL, map);
    }

    /**
     * 新增/修改厂家页面跳转
     * @param providerId
     * @param map
     * @return
     */
    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "providerId", required = false) Integer providerId,
                              Map<String, Object> map) {
        ProviderInfo providerInfo = new ProviderInfo();
        if (providerId != null) {
            providerInfo = providerService.findOne(providerId);
        }
        map.put("providerInfo", providerInfo);
        map.put("pageUrl", URLConstant.PROVIDER_LIST_URL);
        return new ModelAndView(URLConstant.PROVIDER_INDEX_MODEL, map);
    }

    /**
     * 新增/修改厂家
     * @param providerForm
     * @param map
     * @param bindingResult
     * @return
     */
    @PostMapping("/save")
    public ModelAndView save(@Valid ProviderForm providerForm,
                             BindingResult bindingResult,
                             Map<String, Object> map) {
        if (bindingResult.hasErrors()) {
            log.error("【新增/修改厂家】出现异常,{}", bindingResult.getFieldError().getDefaultMessage());
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            map.put("url", URLConstant.PROVIDER_LIST_URL);
            return new ModelAndView(URLConstant.ERROR_URL_MODEL, map);
        }

        ProviderInfo providerInfo = new ProviderInfo();

        if (providerForm.getProviderId() != null) {
            providerInfo = providerService.findOne(providerForm.getProviderId());
        }
        BeanUtils.copyProperties(providerForm, providerInfo);
        log.info("【新增/修改厂家】providerInfo={}", JsonUtil.object2Json(providerInfo));
        providerService.save(providerInfo);
        return new ModelAndView("redirect:".concat(URLConstant.PROVIDER_LIST_URL));
    }

    /**
     * 删除厂家
     * @param providerId
     * @return
     */
    @GetMapping("/delete")
    public ModelAndView delete(@RequestParam("providerId") Integer providerId,
                               Map<String, Object> map) {
        try {
            providerService.delete(providerId);
        } catch (ProviderException e) {
            map.put("msg", e.getMessage());
            map.put("url", URLConstant.PROVIDER_LIST_URL);
            return new ModelAndView(URLConstant.ERROR_URL_MODEL, map);
        }
        return new ModelAndView("redirect:".concat(URLConstant.PROVIDER_LIST_URL));
    }

}
