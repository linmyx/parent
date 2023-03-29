package com.linmyx.service_order.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.linmyx.commonUtils.JwtUtils;
import com.linmyx.commonUtils.Result;
import com.linmyx.service_order.entity.Order;
import com.linmyx.service_order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-11-14
 */
@RestController
@RequestMapping("/eduorder/order")
//@CrossOrigin
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 生成订单方法
     */
    @PostMapping("/createOrder/{courseId}")
    public Result createOrder(@PathVariable String courseId, HttpServletRequest request){
        return orderService.createOrder(courseId,request);
    }

    /**
     * 根据订单id查询订单
     */
    @GetMapping("/getOrderInfo/{orderId}")
    public Result getOrderInfo(@PathVariable String orderId){
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_no",orderId);
        Order order = orderService.getOne(queryWrapper);
        return Result.ok(order);
    }

    /**
     * 根据课程id和用户id，去查询订单表。查询这个订单状态
     */
    @GetMapping("/isBuyCourse/{courseId}/{userId}")
    public Boolean isBuyCourse(@PathVariable String courseId,@PathVariable String userId){

        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id",courseId);
        queryWrapper.eq("member_id",userId);
        queryWrapper.eq("status",1);
        int count = orderService.count(queryWrapper);
        if (count > 0) {
            return true;
        }
        return false;
    }
}

