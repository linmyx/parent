package com.linmyx.service_order.controller;

import com.linmyx.commonUtils.Result;
import com.linmyx.service_order.entity.vo.OrderVo;
import com.linmyx.service_order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/eduorder/orderAdmin")
public class OrderAdminController {
     @Autowired
    private OrderService orderService;

     @PostMapping ("/getAllOrder/{page}/{limit}")
    public Result getAllOrder(@PathVariable long page, @PathVariable long limit,
                              @RequestBody(required = false) OrderVo orderVo){
         return orderService.getAllOrder(page,limit,orderVo);
     }
}
