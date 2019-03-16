package com.repository.core.adapt;

import com.repository.core.constants.URLConstant;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 设置默认首页
 * @author schuyler
 */
@Configuration
public class DefaultView implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:".concat(URLConstant.PROVIDER_LIST_URL));
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }
}
