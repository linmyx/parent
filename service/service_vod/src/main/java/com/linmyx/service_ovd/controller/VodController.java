package com.linmyx.service_ovd.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.linmyx.commonUtils.Result;

import com.linmyx.service_ovd.service.VodService;
import com.linmyx.service_ovd.utils.ConstantPropertiesUtil;
import com.linmyx.service_ovd.utils.InitObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/eduvod/video")
//@CrossOrigin
public class VodController {
    @Autowired
    private VodService vodService;

    //上传视频
    @PostMapping("/uploadAlyVideo")
    public Result uploadAlyVideo(MultipartFile file){
        return vodService.uploadAlyVideo(file);
    }

    /**
     * 根据视频id，删除阿里云视频
     */
    @DeleteMapping("/removeAlyVideo/{id}")
    public Result removeAlyVideo(@PathVariable String id){
        return vodService.removeAlyVideo(id);
    }

    @DeleteMapping("/deleteBatch")
    public Result deleteBatch(@RequestParam("VideoIdList")List<String> VideoIdList){
        return vodService.deleteBatch(VideoIdList);
    }

    /**
     * 根据视频id获取视频凭证
     */
    @GetMapping("/getPlayAuth/{id}")
    public Result getPlayAuth(@PathVariable String id){
        try {
            DefaultAcsClient client =
                    InitObject.initVodClient(ConstantPropertiesUtil.ACCESS_KEY_ID, ConstantPropertiesUtil.ACCESS_KEY_SECRET);

            GetVideoPlayAuthRequest request=new GetVideoPlayAuthRequest();
            request.setVideoId(id);
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            String playAuth = response.getPlayAuth();
            return Result.ok(playAuth);
        }catch (Exception e) {
            throw new RuntimeException("获取视频凭证id失败");
        }
    }

}
