package com.linmyx.service_edu.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "service-order")
@Component
public interface OrderClient {

    @GetMapping("/eduorder/order/isBuyCourse/{courseId}/{userId}")
    public Boolean isBuyCourse(@PathVariable("courseId") String courseId, @PathVariable("userId") String userId);

}
