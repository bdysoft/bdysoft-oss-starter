package com.bdysoft.starter.oss.cloud;

import com.alibaba.fastjson.JSONObject;
import com.bdysoft.starter.oss.CloudStorageConfigProperties;
import com.bdysoft.starter.oss.enums.OssTypeEnum;
import com.bdysoft.starter.oss.result.CloudStorageUploadResult;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.request.UploadFileRequest;
import com.qcloud.cos.sign.Credentials;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * 腾讯云存储
 *
 * @author Lvwei lvwei@bdysoft.com
 */
public class QcloudAbstractCloudStorageService extends AbstractCloudStorageService {
    private COSClient client;

    public QcloudAbstractCloudStorageService(CloudStorageConfigProperties config) {
        this.config = config;
        init();
    }

    private void init() {
        Credentials credentials = new Credentials(config.getQcloudAppId(), config.getQcloudSecretId(),
                config.getQcloudSecretKey());

        //初始化客户端配置
        ClientConfig clientConfig = new ClientConfig();
        //设置bucket所在的区域，华南：gz 华北：tj 华东：sh
        clientConfig.setRegion(config.getQcloudRegion());

        client = new COSClient(clientConfig, credentials);
    }

    @Override
    public CloudStorageUploadResult upload(byte[] data, String path) {
        //腾讯云必需要以"/"开头
        if (!path.startsWith("/")) {
            path = "/" + path;
        }
        CloudStorageUploadResult cloudStorageUploadResult = new CloudStorageUploadResult();
        cloudStorageUploadResult.setStorage(OssTypeEnum.QCLOUD.getOssName());
        cloudStorageUploadResult.setDomain(config.getQcloudDomain());
        cloudStorageUploadResult.setExternalUrl(config.getQcloudDomain() + "/" + path);
        cloudStorageUploadResult.setPreviewUrl(config.getAliyunDomain() + "/" + path);
        cloudStorageUploadResult.setFilePath(path);
        //上传到腾讯云
        UploadFileRequest request = new UploadFileRequest(config.getQcloudBucketName(), path, data);
        String response = client.uploadFile(request);
        JSONObject jsonObject = JSONObject.parseObject(response);
        if (jsonObject.getInteger("code") != 0) {
            cloudStorageUploadResult.setUploadSuccess(false);
            cloudStorageUploadResult.setErrorMessage(jsonObject.getString("message"));
        } else {
            cloudStorageUploadResult.setUploadSuccess(true);
        }
        return cloudStorageUploadResult;
    }

    @Override
    public CloudStorageUploadResult upload(InputStream inputStream, String path) {
        CloudStorageUploadResult cloudStorageUploadResult = new CloudStorageUploadResult();
        cloudStorageUploadResult.setStorage(OssTypeEnum.QCLOUD.getOssName());
        cloudStorageUploadResult.setDomain(config.getQcloudDomain());
        try {
            byte[] data = IOUtils.toByteArray(inputStream);
            return this.upload(data, path);
        } catch (IOException e) {
            cloudStorageUploadResult.setUploadSuccess(false);
            cloudStorageUploadResult.setErrorMessage(e.getMessage());
        }
        return cloudStorageUploadResult;
    }

    @Override
    public CloudStorageUploadResult uploadSuffix(byte[] data, String suffix) {
        String path = getPath(config.getQcloudPrefix(), suffix);
        return upload(data, path);
    }

    @Override
    public CloudStorageUploadResult uploadSuffix(InputStream inputStream, String suffix) {
        return upload(inputStream, getPath(config.getQcloudPrefix(), suffix));
    }
}
