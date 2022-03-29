package com.bdysoft.starter.oss.cloud;

import com.bdysoft.starter.oss.CloudStorageConfigProperties;
import com.bdysoft.starter.oss.result.CloudStorageUploadResult;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * 七牛云存储
 *
 * @author Lvwei lvwei@bdysoft.com
 */
public class QiniuAbstractCloudStorageService extends AbstractCloudStorageService {
    private UploadManager uploadManager;
    private final static String STORAGE = "qiniu";
    private String token;

    public QiniuAbstractCloudStorageService(CloudStorageConfigProperties config) {
        this.config = config;        //初始化
        init();
    }

    private void init() {
        uploadManager = new UploadManager(new Configuration(Zone.autoZone()));
        token = Auth.create(config.getQiniuAccessKey(), config.getQiniuSecretKey()).
                uploadToken(config.getQiniuBucketName());
    }

    @Override
    public CloudStorageUploadResult upload(byte[] data, String path) {
        CloudStorageUploadResult cloudStorageUploadResult = new CloudStorageUploadResult();
        cloudStorageUploadResult.setStorage(STORAGE);
        cloudStorageUploadResult.setDomain(config.getQiniuDomain());
        cloudStorageUploadResult.setExternalUrl(config.getQiniuDomain() + "/" + path);
        cloudStorageUploadResult.setPreviewUrl(config.getAliyunDomain() + "/" + path);
        cloudStorageUploadResult.setFilePath(path);
        try {
            Response res = uploadManager.put(data, path, token);
            if (!res.isOK()) {
                cloudStorageUploadResult.setUploadSuccess(false);
                cloudStorageUploadResult.setUploadSuccess(false);
                cloudStorageUploadResult.setErrorMessage(res.getInfo());
            } else {
                cloudStorageUploadResult.setUploadSuccess(true);
            }
        } catch (Exception e) {
            cloudStorageUploadResult.setUploadSuccess(false);
            cloudStorageUploadResult.setErrorMessage(e.getMessage());
        }
        return cloudStorageUploadResult;
    }

    @Override
    public CloudStorageUploadResult upload(InputStream inputStream, String path) {
        CloudStorageUploadResult cloudStorageUploadResult = new CloudStorageUploadResult();
        cloudStorageUploadResult.setStorage(STORAGE);
        cloudStorageUploadResult.setDomain(config.getQiniuDomain());
        try {
            byte[] data = IOUtils.toByteArray(inputStream);
            return this.upload(data, path);
        } catch (IOException e) {
            cloudStorageUploadResult.setErrorMessage(e.getMessage());
            cloudStorageUploadResult.setUploadSuccess(false);
        }
        return cloudStorageUploadResult;

    }

    @Override
    public CloudStorageUploadResult uploadSuffix(byte[] data, String suffix) {
        String path = getPath(config.getQiniuPrefix(), suffix);
        return upload(data, path);
    }

    @Override
    public CloudStorageUploadResult uploadSuffix(InputStream inputStream, String suffix) {
        return upload(inputStream, getPath(config.getQiniuPrefix(), suffix));
    }
}
