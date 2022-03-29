package com.bdysoft.starter.oss.result;

/**
 * 文件上传结果
 */
public class CloudStorageUploadResult {
    /**
     * 外部链接
     */
    public String externalUrl;
    /**
     * 存储域名
     */
    public String domain;
    /**
     * 存储方式
     */
    public String storage;
    /**
     * 上传结果
     */
    public Boolean uploadSuccess;

    /**
     * 文件路径
     */
    private String filePath;
    /**
     * 预览地址
     */
    public String previewUrl;

    /**
     * 错误信息
     */
    public String errorMessage;

    public CloudStorageUploadResult() {
        this.uploadSuccess = false;
        this.errorMessage = "";
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Boolean getUploadSuccess() {
        return uploadSuccess;
    }

    public void setUploadSuccess(Boolean uploadSuccess) {
        this.uploadSuccess = uploadSuccess;
    }

    public String getExternalUrl() {
        return externalUrl;
    }

    public void setExternalUrl(String externalUrl) {
        this.externalUrl = externalUrl;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }


    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getPreviewUrl() {
        return previewUrl;
    }

    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
    }
}
