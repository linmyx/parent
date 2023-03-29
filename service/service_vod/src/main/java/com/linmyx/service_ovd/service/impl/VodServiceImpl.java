package com.linmyx.service_ovd.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.linmyx.commonUtils.Result;
import com.linmyx.service_ovd.service.VodService;
import com.linmyx.service_ovd.utils.ConstantPropertiesUtil;
import com.linmyx.service_ovd.utils.InitObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


@Service
public class VodServiceImpl implements VodService {
    @Override
    public Result uploadAlyVideo(MultipartFile file) {
        try {
            String fileName=file.getOriginalFilename();
            String title = fileName.substring(0, fileName.lastIndexOf("."));
            InputStream inputStream = file.getInputStream();
            UploadStreamRequest request = new UploadStreamRequest(ConstantPropertiesUtil.ACCESS_KEY_ID, ConstantPropertiesUtil.ACCESS_KEY_SECRET, title, fileName, inputStream);
            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);
            String videoId="";
            if (response.isSuccess()) {
                videoId=response.getVideoId();
            } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
               videoId=response.getVideoId();
            }
            //返回上传视频的id
            return Result.ok(videoId);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 删除云端视频
     * @param id
     * @return
     */
    @Override
    public Result removeAlyVideo(String id) {
        try {
            DefaultAcsClient client = InitObject.initVodClient(ConstantPropertiesUtil.ACCESS_KEY_ID, ConstantPropertiesUtil.ACCESS_KEY_SECRET);
            DeleteVideoRequest request = new DeleteVideoRequest();
            request.setVideoIds(id);
            //调用初始化对象的方法
            client.getAcsResponse(request);
            return Result.ok();
        } catch (ClientException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Result deleteBatch(List<String> videoIdList) {
        try {
            DefaultAcsClient client = InitObject.initVodClient(ConstantPropertiesUtil.ACCESS_KEY_ID, ConstantPropertiesUtil.ACCESS_KEY_SECRET);
            DeleteVideoRequest request = new DeleteVideoRequest();
            String ids = StringUtils.join(videoIdList, ",");
            request.setVideoIds(ids);
            //调用初始化对象的方法
            client.getAcsResponse(request);
            return Result.ok();
        } catch (ClientException e) {
            throw new RuntimeException(e);
        }
    }
}
