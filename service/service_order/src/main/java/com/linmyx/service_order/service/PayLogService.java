package com.linmyx.service_order.service;

import com.linmyx.commonUtils.Result;
import com.linmyx.service_order.entity.PayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-11-14
 */
public interface PayLogService extends IService<PayLog> {

    Result createNative(String orderNo);

   

    /**
     * 查询订单状态
     * @param orderNo
     * @return
     */
    Map<String, String> queryPayStatus(String orderNo);

    void updateOrderStatus(Map<String, String> map);
}
