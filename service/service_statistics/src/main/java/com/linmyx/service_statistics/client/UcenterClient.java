package com.linmyx.service_statistics.client;

import com.linmyx.commonUtils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(name = "service-ucenter")
public interface UcenterClient {

    @GetMapping("/educenter/member/countRegister/{day}")
    public Result countRegister(@PathVariable("day") String day);

}
