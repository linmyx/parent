package com.linmyx.oss.controller;

import com.linmyx.commonUtils.Result;
import com.linmyx.oss.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/eduoss/fileoss")
//@CrossOrigin
public class OssController {

    @Autowired
    private OssService ossService;

    @PostMapping
    public Result uploadFile(MultipartFile file){
        //返回上传到oss的路径
        String url=ossService.uploadFileAvatar(file);
        return Result.ok(url);
    }

}
