# bdysoft-oss-starter

#### 介绍
一款支持七牛云、腾讯云、阿里云文件上传的starter

#### 安装说明
下载代码后，执行 mvn clean install 安装到本地仓库


#### 使用说明
1. 在pom.xml 添加依赖
```xml
<dependency>
    <groupId>com.bdysoft</groupId>
    <artifactId>shop-cloud-starter-oss</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```
2.在yaml文件中配置参数
```yaml
oss:
  type: 
  qiniu-access-key: 
  qiniu-secret-key: 
  qiniu-domain: 
  qiniu-bucket-name: 
  qiniu-prefix:
```
3.在代码中直接使用文件上传
```java
// 获取文件原始名
String originalFilename = file.getOriginalFilename();
// 获取文件后缀名
String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
// 文件上传
CloudStorageUploadResult uploadResult = cloudStorageService.build().uploadSuffix(file.getBytes(), suffix);
```

