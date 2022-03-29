package com.bdysoft.starter.oss;

import com.bdysoft.starter.oss.cloud.AbstractCloudStorageService;
import com.bdysoft.starter.oss.cloud.AliyunAbstractCloudStorageService;
import com.bdysoft.starter.oss.cloud.QcloudAbstractCloudStorageService;
import com.bdysoft.starter.oss.cloud.QiniuAbstractCloudStorageService;

/**
 * @author lvwei
 */
public class CloudStorageService {
    private final CloudStorageConfigProperties config;

    public CloudStorageService(CloudStorageConfigProperties cloudStorageConfigProperties) {
        this.config = cloudStorageConfigProperties;
        build();
    }

    public AbstractCloudStorageService build() {
        if (this.config.getType() == 1) {
            return new QiniuAbstractCloudStorageService(config);
        }
        if (config.getType() == 2) {
            return new AliyunAbstractCloudStorageService(config);
        }
        if (config.getType() == 3) {
            return new QcloudAbstractCloudStorageService(config);
        }
        return null;
    }
}
