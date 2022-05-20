package com.bdysoft.starter.oss.cloud;

import com.aliyun.oss.OSSClient;
import com.bdysoft.starter.oss.CloudStorageConfigProperties;
import com.bdysoft.starter.oss.enums.OssTypeEnum;
import com.bdysoft.starter.oss.result.CloudStorageUploadResult;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * 阿里云存储
 *
 * @author Lvwei lvwei@bdysoft.com
 */
public class AliyunAbstractCloudStorageService extends AbstractCloudStorageService {
    private OSSClient client;

    public AliyunAbstractCloudStorageService(CloudStorageConfigProperties config) {
        this.config = config;
        //初始化
        init();
    }

    private void init() {
        client = new OSSClient(config.getAliyunEndPoint(), config.getAliyunAccessKeyId(),
                config.getAliyunAccessKeySecret());
    }

    @Override
    public CloudStorageUploadResult upload(byte[] data, String path) {
        return upload(new ByteArrayInputStream(data), path);
    }

    @Override
    public CloudStorageUploadResult upload(InputStream inputStream, String path) {
        CloudStorageUploadResult cloudStorageUploadResult = new CloudStorageUploadResult();
        cloudStorageUploadResult.setStorage(OssTypeEnum.ALIYUN.getOssName());
        cloudStorageUploadResult.setDomain(config.getAliyunDomain());
        cloudStorageUploadResult.setExternalUrl(config.getAliyunDomain() + "/" + path);
        cloudStorageUploadResult.setPreviewUrl(config.getAliyunDomain() + "/" + path);
        cloudStorageUploadResult.setFilePath(path);
        try {
            client.putObject(config.getAliyunBucketName(), path, inputStream);
            cloudStorageUploadResult.setUploadSuccess(true);
        } catch (Exception e) {
            cloudStorageUploadResult.setUploadSuccess(false);
            cloudStorageUploadResult.setErrorMessage(e.getMessage());
        }
        return cloudStorageUploadResult;
    }

    @Override
    public CloudStorageUploadResult uploadSuffix(byte[] data, String suffix) {
        String path = getPath(config.getAliyunPrefix(), suffix);
        return upload(data, path);
    }

    @Override
    public CloudStorageUploadResult uploadSuffix(InputStream inputStream, String suffix) {
        return upload(inputStream, getPath(config.getAliyunPrefix(), suffix));
    }

}
