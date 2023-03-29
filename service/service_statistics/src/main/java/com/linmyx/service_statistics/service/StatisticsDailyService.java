package com.linmyx.service_statistics.service;

import com.linmyx.commonUtils.Result;
import com.linmyx.service_statistics.entity.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-11-15
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {

    Result registerCount(String day);

    Result showData(String type, String begin, String end);

    Result showAllData(String begin, String end);
}
