package com.linmyx.service_cms.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.linmyx.commonUtils.Result;
import com.linmyx.service_cms.entity.CrmBanner;
import com.linmyx.service_cms.mapper.CrmBannerMapper;
import com.linmyx.service_cms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-11-11
 */
@Service
@Slf4j
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    @Autowired
   private StringRedisTemplate stringRedisTemplate;
    @Override
    public Result getAllBanner(){
        String banner1 = stringRedisTemplate.opsForValue().get("IndexBanner");
        if (!StringUtils.isEmpty(banner1)) {
            List<CrmBanner> list = JSON.parseObject(banner1, List.class);
            return Result.ok(list);
        } else {
            QueryWrapper<CrmBanner> queryWrap=new QueryWrapper<>();
            queryWrap.orderByDesc("id");
            queryWrap.last("limit 2");
            List<CrmBanner> crmBannerList = list(queryWrap);
            String banner = JSON.toJSONString(crmBannerList);
            stringRedisTemplate.opsForValue().set("IndexBanner",banner);
            return Result.ok(crmBannerList);
        }
    }

    /**
     * 后端获取所有Banner
     * @param page
     * @param limit
     * @return
     */
    @Override
    public Result pageBanner(long page, long limit) {
        Page<CrmBanner> bannerInfo=new Page<>(page,limit);
        page(bannerInfo,null);
        Map<String,Object> map=new HashMap<>();
        map.put("total",bannerInfo.getTotal());
        map.put("rows",bannerInfo.getRecords());
        long total = bannerInfo.getTotal();
        log.info("total:{}",total);
        return Result.ok(map);
    }

}
