package com.bdysoft.starter.oss;

import com.bdysoft.starter.oss.cloud.*;
import com.bdysoft.starter.oss.enums.OssTypeEnum;

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
        if (this.config.getType().equals(OssTypeEnum.QINIU.getOssType())) {
            return new QiniuAbstractCloudStorageService(config);
        }
        if (config.getType().equals(OssTypeEnum.ALIYUN.getOssType())) {
            return new AliyunAbstractCloudStorageService(config);
        }
        if (config.getType().equals(OssTypeEnum.QCLOUD.getOssType())) {
            return new QcloudAbstractCloudStorageService(config);
        }
        if (config.getType().equals(OssTypeEnum.MINIIO.getOssType())){
            return new MiniIoAbstractCloudStorageService(config);
        }
        return null;
    }
}
