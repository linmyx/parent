package com.linmyx.service_ucenter.entity.vo;

import io.swagger.models.auth.In;
import lombok.Data;

import java.util.Date;

@Data
public class UserQuery {
    private String mobile;
    private Integer sex;
    private String begin;
    private String end;
}
