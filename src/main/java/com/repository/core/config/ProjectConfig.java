package com.repository.core.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 项目相关配置
 * @author schuyler
 */
@ConfigurationProperties(prefix = "project")
@Data
@Component
public class ProjectConfig {
    private String url;
    private String picturePath;
    private String pictureUrl;
}
