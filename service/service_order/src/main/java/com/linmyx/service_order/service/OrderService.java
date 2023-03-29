package com.linmyx.service_order.service;

import com.linmyx.commonUtils.Result;
import com.linmyx.service_order.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;
import com.linmyx.service_order.entity.vo.OrderVo;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-11-14
 */
public interface OrderService extends IService<Order> {

    Result createOrder(String courseId, HttpServletRequest request);

    Result getAllOrder(long page, long limit, OrderVo orderVo);
}
