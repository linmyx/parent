package com.linmyx.service_edu.client;

import com.linmyx.commonUtils.Result;

import com.linmyx.service_edu.entity.UcenterMember;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Component
@FeignClient(name = "service-ucenter")
public interface UcenterClient {

    @PostMapping("/educenter/member/getUserInfo/{id}")
    UcenterMember getUserInfo(@PathVariable("id") String id);

}
