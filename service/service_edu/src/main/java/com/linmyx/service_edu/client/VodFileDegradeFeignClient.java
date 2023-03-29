package com.linmyx.service_edu.client;

import com.linmyx.commonUtils.Result;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VodFileDegradeFeignClient implements VodClient{
    @Override
    public Result removeAlyVideo(String id) {
        return Result.error("删除失败，请重试");
    }

    @Override
    public Result deleteBatch(List<String> VideoIdList) {
        return Result.error("删除失败，请重试");
    }
}
