package com.linmyx.service_order.client;

import com.linmyx.commonUtils.orderVo.UcenterMemberOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Component
@FeignClient(name = "service-ucenter")
public interface UserClient {

    @PostMapping("/educenter/member/getUserInfoByOrder/{id}")
    public UcenterMemberOrder getUserInfoByOrder(@PathVariable("id") String id);
}
