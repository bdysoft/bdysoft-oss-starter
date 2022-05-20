package com.bdysoft.starter.oss.cloud;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.bdysoft.starter.oss.CloudStorageConfigProperties;
import com.bdysoft.starter.oss.result.CloudStorageUploadResult;

import java.io.InputStream;
import java.util.Date;

/**
 * 云存储(支持七牛、阿里云、腾讯云)
 *
 * @author Lvwei lvwei@bdysoft.com
 */
public abstract class AbstractCloudStorageService {
    /**
     * 云存储配置信息
     */
    CloudStorageConfigProperties config;

    /**
     * 文件路径
     *
     * @param prefix 前缀
     * @param suffix 后缀
     * @return 返回上传路径
     */
    public String getPath(String prefix, String suffix) {
        //文件路径
        String path = DateUtil.format(new Date(), "yyyyMMddhhmmss") + "/" + RandomUtil.randomNumbers(8);
        if (StrUtil.isNotBlank(prefix)) {
            path = prefix + "/" + path;
        }
        return path + suffix;
    }

    /**
     * 文件上传
     *
     * @param data 文件字节数组
     * @param path 文件路径，包含文件名
     * @return 返回上传结果
     */
    public abstract CloudStorageUploadResult upload(byte[] data, String path);

    /**
     * 文件上传
     *
     * @param data   文件字节数组
     * @param suffix 后缀
     * @return 返回上传结果
     */
    public abstract CloudStorageUploadResult uploadSuffix(byte[] data, String suffix);

    /**
     * 文件上传
     *
     * @param inputStream 字节流
     * @param path        文件路径，包含文件名
     * @return 返回上传结果
     */
    public abstract CloudStorageUploadResult upload(InputStream inputStream, String path);

    /**
     * 文件上传
     *
     * @param inputStream 字节流
     * @param suffix      后缀
     * @return 返回上传结果
     */
    public abstract CloudStorageUploadResult uploadSuffix(InputStream inputStream, String suffix);
}
