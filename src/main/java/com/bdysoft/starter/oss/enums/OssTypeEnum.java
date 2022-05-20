package com.bdysoft.starter.oss.enums;

/**
 * OSS类型枚举
 *
 * @author 吕伟
 */

public enum OssTypeEnum {

    QINIU(1, "七牛云"),
    ALIYUN(2, "阿里云"),
    QCLOUD(3, "腾讯云"),
    MINIIO(4, "MiniIo");


    private Integer ossType;
    private String ossName;

    OssTypeEnum(Integer ossType, String ossName) {
        this.ossType = ossType;
        this.ossName = ossName;
    }

    public Integer getOssType() {
        return ossType;
    }

    public void setOssType(Integer ossType) {
        this.ossType = ossType;
    }

    public String getOssName() {
        return ossName;
    }

    public void setOssName(String ossName) {
        this.ossName = ossName;
    }
}
