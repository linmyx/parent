package com.linmyx.service_order.entity.vo;

import lombok.Data;

import java.util.Date;

@Data
public class OrderVo {
    private String CourseTitle;
    private Integer payType;
    private String mobile;
    private String begin;
    private String end;
}
