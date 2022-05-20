package com.bdysoft.starter.oss.cloud;

import com.bdysoft.starter.oss.CloudStorageConfigProperties;
import com.bdysoft.starter.oss.enums.OssTypeEnum;
import com.bdysoft.starter.oss.result.CloudStorageUploadResult;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * @author lvwei
 * @description MiniIo存储
 * @date 2022/5/19 20:48
 */
public class MiniIoAbstractCloudStorageService extends AbstractCloudStorageService {

    private MinioClient client;

    public MiniIoAbstractCloudStorageService(CloudStorageConfigProperties config) {
        this.config = config;
        init();
    }

    // 初始化
    private void init() {
        client = MinioClient.builder()
                .endpoint(config.getMiniIoEndpoint())
                .credentials(config.getMiniIoAccesskey(), config.getMiniIoSecretKey())
                .build();
    }

    /**
     * 文件上传
     *
     * @param data 文件字节数组
     * @param path 文件路径，包含文件名
     * @return 返回上传结果
     */
    @Override
    public CloudStorageUploadResult upload(byte[] data, String path) {
        return upload(new ByteArrayInputStream(data), path);
    }

    /**
     * 文件上传
     *
     * @param inputStream 字节流
     * @param path        文件路径，包含文件名
     * @return 返回上传结果
     */
    @Override
    public CloudStorageUploadResult upload(InputStream inputStream, String path) {
        CloudStorageUploadResult cloudStorageUploadResult = new CloudStorageUploadResult();
        cloudStorageUploadResult.setStorage(OssTypeEnum.MINIIO.getOssName());
        cloudStorageUploadResult.setDomain(config.getMiniIoDomain());
        cloudStorageUploadResult.setExternalUrl(config.getMiniIoDomain() + "/" + path);
        cloudStorageUploadResult.setPreviewUrl(config.getMiniIoDomain() + "/" + path);
        cloudStorageUploadResult.setFilePath(path);
        try {
            client.putObject(
                    PutObjectArgs.builder()
                            .bucket(config.getMinioBucket())
                            .object(path)
                            .stream(inputStream, inputStream.available(), -1)
                            .build());
            cloudStorageUploadResult.setUploadSuccess(true);
        } catch (Exception e) {
            cloudStorageUploadResult.setUploadSuccess(false);
            cloudStorageUploadResult.setErrorMessage(e.getMessage());
        }
        return cloudStorageUploadResult;
    }

    /**
     * 文件上传
     *
     * @param data   文件字节数组
     * @param suffix 后缀
     * @return 返回上传结果
     */
    @Override
    public CloudStorageUploadResult uploadSuffix(byte[] data, String suffix) {
        String path = getPath(config.getAliyunPrefix(), suffix);
        return upload(data, path);
    }

    /**
     * 文件上传
     *
     * @param inputStream 字节流
     * @param suffix      后缀
     * @return 返回上传结果
     */
    @Override
    public CloudStorageUploadResult uploadSuffix(InputStream inputStream, String suffix) {
        return upload(inputStream, getPath(config.getAliyunPrefix(), suffix));
    }
}
