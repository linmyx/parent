package com.linmyx.service_order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.linmyx.commonUtils.JwtUtils;
import com.linmyx.commonUtils.Result;
import com.linmyx.commonUtils.orderVo.CourseWebOrderVo;
import com.linmyx.commonUtils.orderVo.UcenterMemberOrder;
import com.linmyx.service_order.client.EduClient;
import com.linmyx.service_order.client.UserClient;
import com.linmyx.service_order.entity.Order;
import com.linmyx.service_order.entity.vo.OrderVo;
import com.linmyx.service_order.mapper.OrderMapper;
import com.linmyx.service_order.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linmyx.service_order.utils.OrderNoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-11-14
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private EduClient eduClient;
    @Autowired
    private UserClient userClient;
    @Override
    public Result createOrder(String courseId, HttpServletRequest request) {

        String userId = JwtUtils.getMemberIdByJwtToken(request);
        //通过远程调用，获取用户信息
        UcenterMemberOrder userInfoByOrder = userClient.getUserInfoByOrder(userId);
        //通过远程调用，获取课程信息
        CourseWebOrderVo courseInfoOrder = eduClient.getCourseInfoOrder(courseId);
        Order order = new Order();
        order.setOrderNo(OrderNoUtil.getOrderNo());
        order.setCourseId(courseId);
        order.setCourseTitle(courseInfoOrder.getTitle());
        order.setCourseCover(courseInfoOrder.getCover());
        order.setTeacherName("test");
        order.setTotalFee(courseInfoOrder.getPrice());
        order.setMemberId(userId);
        order.setMobile(userInfoByOrder.getMobile());
        order.setNickname(userInfoByOrder.getNickname());
        order.setStatus(0);
        order.setPayType(1);
        boolean save = save(order);
        if ( !save) {
            return Result.error("订单生成失败!!");
        }
        return Result.ok(order.getOrderNo());
    }

    /**
     * 后台页面获取所有的订单列表
     *
     * @param page
     * @param limit
     * @param orderVo
     * @return
     */
    @Override
    public Result getAllOrder(long page, long limit, OrderVo orderVo) {
        Page<Order> orderInfo=new Page<>(page, limit);
        QueryWrapper<Order> queryWrap=new QueryWrapper<>();
        if (!StringUtils.isEmpty(orderVo.getCourseTitle())){
            queryWrap.like("course_title",orderVo.getCourseTitle());
        }
        if (!StringUtils.isEmpty(orderVo.getMobile())){
            queryWrap.eq("mobile",orderVo.getMobile());
        }
        if (orderVo.getPayType() > 0){
            queryWrap.eq("pay_type",orderVo.getPayType());
        }
        if (!StringUtils.isEmpty(orderVo.getBegin()) && !StringUtils.isEmpty(orderVo.getEnd())){
            queryWrap.between("gmt_modified",orderVo.getBegin(),orderVo.getEnd());
        }
        page(orderInfo,queryWrap);
        Map<String,Object> map=new HashMap<>();
        map.put("total",orderInfo.getTotal());
        map.put("rows",orderInfo.getRecords());
        return Result.ok(map);
    }
}
