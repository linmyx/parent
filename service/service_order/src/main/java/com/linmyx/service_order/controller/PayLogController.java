package com.linmyx.service_order.controller;


import com.linmyx.commonUtils.Result;
import com.linmyx.service_order.service.PayLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-11-14
 */
@RestController
@RequestMapping("/eduorder/paylog")
//@CrossOrigin
public class PayLogController {

    @Autowired
    private PayLogService payLogService;

    @GetMapping("/createNative/{orderNo}")
    public Result createNative(@PathVariable String orderNo){
        return payLogService.createNative(orderNo);
    }

    //查询订单的支付状态
    @GetMapping("/queryPayStatus/{orderNo}")
    public Result queryPayStatus(@PathVariable String orderNo){
       Map<String,String> map= payLogService.queryPayStatus(orderNo);
       if (map == null){
           return Result.error("支付错误，请重新支付");
       }
       //如果map里面不为空，通过map获取值
        if (map.get("trade_state").equals("SUCCESS")){
            payLogService.updateOrderStatus(map);
            return Result.ok();
        }
        return Result.error("支付中");
    }
}

