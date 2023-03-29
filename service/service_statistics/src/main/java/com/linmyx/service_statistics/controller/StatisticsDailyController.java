package com.linmyx.service_statistics.controller;


import com.linmyx.commonUtils.Result;
import com.linmyx.service_statistics.service.StatisticsDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-11-15
 */
@RestController
@RequestMapping("/staservice/sta")
//@CrossOrigin
public class StatisticsDailyController {
    @Autowired
    private StatisticsDailyService staService;

    @PostMapping("/registerCount/{day}")
    public Result registerCount(@PathVariable String day){
        return staService.registerCount(day);
    }

    @GetMapping("/showData/{type}/{begin}/{end}")
    public Result showData(@PathVariable String type,@PathVariable String begin,@PathVariable String end){
       return staService.showData(type,begin,end);
    }

    @GetMapping("/showAllData/{begin}/{end}")
    public Result showAllData(@PathVariable String begin,@PathVariable String end){
        return staService.showAllData(begin,end);
    }

}

