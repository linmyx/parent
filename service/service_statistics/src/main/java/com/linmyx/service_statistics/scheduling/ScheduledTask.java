package com.linmyx.service_statistics.scheduling;

import com.linmyx.service_statistics.service.StatisticsDailyService;
import com.linmyx.service_statistics.utils.DateUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 定时任务
 */
@Component
public class ScheduledTask {

    @Autowired
    private StatisticsDailyService staService;



    //每天凌晨对前一天的数据进行查询
    @Scheduled(cron = "0 0 1 * * ?")
    public void task2(){
        staService.registerCount(DateUtil.formatDate(DateUtil.addDays(new Date(),-1)));
    }



}
