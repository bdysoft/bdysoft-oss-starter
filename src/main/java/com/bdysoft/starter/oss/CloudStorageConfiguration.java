package com.bdysoft.starter.oss;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 自动装配类
 *
 * @Configuration：标识此类为配置类
 * @ConditionalOnClass: 表示只有指定的class在classpath上时才能被注册
 * @EnableConfigurationProperties: 激活@ConfigurationProperties
 */
@Configuration
@ConditionalOnClass(CloudStorageService.class)
@EnableConfigurationProperties(CloudStorageConfigProperties.class)
public class CloudStorageConfiguration {

    private final CloudStorageConfigProperties config;

    /**
     * 自动注入配置类
     *
     * @param config
     */
    public CloudStorageConfiguration(CloudStorageConfigProperties config) {
        this.config = config;
    }

    /**
     * 创建CloudStorageService对象，注入到Spring容器中
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(CloudStorageService.class) // 当容器没有此bean时，才注册
    public CloudStorageService cloudStorageService() {
        CloudStorageService cloudStorageService = new CloudStorageService(config);
        return cloudStorageService;
    }


}
