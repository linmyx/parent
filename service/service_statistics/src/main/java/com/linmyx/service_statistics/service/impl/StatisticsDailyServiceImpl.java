package com.linmyx.service_statistics.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.client.utils.JSONUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.linmyx.commonUtils.Result;
import com.linmyx.service_statistics.client.UcenterClient;
import com.linmyx.service_statistics.entity.StatisticsDaily;
import com.linmyx.service_statistics.mapper.StatisticsDailyMapper;
import com.linmyx.service_statistics.service.StatisticsDailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.swagger.models.auth.In;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-11-15
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {

    @Autowired
    UcenterClient ucenterClient;

    @Override
    public Result registerCount(String day) {
        Result result = ucenterClient.countRegister(day);
        Integer registerCount = (Integer) result.getData();

        QueryWrapper<StatisticsDaily> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("date_calculated", day);
        remove(queryWrapper);
        //把获取到的数据添加到数据库当中
        StatisticsDaily statistics=new StatisticsDaily();
        statistics.setRegisterNum(registerCount);
        statistics.setDateCalculated(day);
        statistics.setVideoViewNum(RandomUtils.nextInt(100,200));
        statistics.setLoginNum(RandomUtils.nextInt(100,200));
        statistics.setCourseNum(RandomUtils.nextInt(100,200));
        save(statistics);
        return Result.ok();
    }

    @Override
    public Result showData(String type, String begin, String end) {
        //根据条件查询对的数据
        QueryWrapper<StatisticsDaily> queryWrap=new QueryWrapper<>();
        queryWrap.between("date_calculated",begin,end);
        queryWrap.select("date_calculated",type);
        List<StatisticsDaily> staList = list(queryWrap);

        List<String> dateList=new ArrayList<>();
        List<Integer> numList=new ArrayList<>();

        for (StatisticsDaily statisticsDaily :staList) {
            dateList.add(statisticsDaily.getDateCalculated());
            switch (type){
                case "login_num":
                    numList.add(statisticsDaily.getLoginNum());
                    break;
                case "register_num":
                    numList.add(statisticsDaily.getRegisterNum());
                    break;
                case "video_view_num":
                    numList.add(statisticsDaily.getVideoViewNum());
                    break;
                case "course_num":
                    numList.add(statisticsDaily.getCourseNum());
                    break;
                default:
                    break;
            }
        }
        Map<String,Object> map=new HashMap<>();
        map.put("dateList",dateList);
        map.put("numList",numList);
        return Result.ok(map);
    }

    @Override
    public Result showAllData(String begin, String end) {
        //根据条件查询对的数据
        QueryWrapper<StatisticsDaily> queryWrap=new QueryWrapper<>();
        queryWrap.between("date_calculated",begin,end);
        queryWrap.select("date_calculated","register_num","login_num","video_view_num","course_num");
        List<StatisticsDaily> staList = list(queryWrap);

        List<String> dateList=new ArrayList<>();
        List<Integer> registerList=new ArrayList<>();
        List<Integer> loginList=new ArrayList<>();
        List<Integer> videoList=new ArrayList<>();
        List<Integer> courseList=new ArrayList<>();
        for (StatisticsDaily stats : staList) {
            dateList.add(stats.getDateCalculated());
            registerList.add(stats.getRegisterNum());
            loginList.add(stats.getLoginNum());
            videoList.add(stats.getVideoViewNum());
            courseList.add(stats.getCourseNum());
        }
        Map<String,Object> map=new HashMap<>();
        map.put("dateList",dateList);
        map.put("registerList",registerList);
        map.put("loginList",loginList);
        map.put("videoList",videoList);
        map.put("courseList",courseList);
        return Result.ok(map);
    }
}
