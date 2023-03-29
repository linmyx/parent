package com.linmyx.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.linmyx.oss.service.OssService;

import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.UUID;

import static com.linmyx.oss.utils.ConstantPropertiesUtils.*;

@Service
public class OssServiceImpl implements OssService {
    /**
     * 文件上传到阿里云oss
     * @param file
     * @return
     */
    @Override
    public String uploadFileAvatar(MultipartFile file) {


        String endpoint =END_POINT;
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = ACCESS_KEY_ID;
        String accessKeySecret = ACCESS_KEY_SECRET;
        // 填写Bucket名称，例如examplebucket。
        String bucketName = BUCKET_NAME;

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        String url="";
        try {
            InputStream inputStream = file.getInputStream();
            String originalFilename = file.getOriginalFilename();
            String uuid = UUID.randomUUID().toString().replace("-", "");

            originalFilename=uuid+originalFilename;
            //2.把文件按照日期进行分类
            //2019/11/12/
            String datePath = new DateTime().toString("yyyy/MM/dd");
            originalFilename=datePath+"/"+originalFilename;
            // 创建PutObject请求。
            //1.第一个参数：阿里云存储桶的名字
            //2.第二个参数：上传文件的名字和路径
            //3.第三个参数：文件上传流
            ossClient.putObject(bucketName,originalFilename, inputStream);
            //把上传之后的路径返回
            Date date = new Date(System.currentTimeMillis() + 36000000 * 100000000);
            //需要把上传到阿里云oss路径手动拼接出来
            url = String.valueOf(ossClient.generatePresignedUrl(bucketName, originalFilename, date));
        } catch (Exception oe) {
            oe.printStackTrace();
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return url;
    }
}
