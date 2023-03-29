package com.linmyx.service_ovd.service;

import com.linmyx.commonUtils.Result;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VodService {
    Result uploadAlyVideo(MultipartFile file);

    Result removeAlyVideo(String id);

    Result deleteBatch(List<String> videoIdList);
}
