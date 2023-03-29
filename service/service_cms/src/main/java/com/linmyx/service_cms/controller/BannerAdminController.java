package com.linmyx.service_cms.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.linmyx.commonUtils.Result;
import com.linmyx.service_cms.entity.CrmBanner;
import com.linmyx.service_cms.service.CrmBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 后台banner管理接口
 * </p>
 *
 * @author testjava
 * @since 2022-11-11
 */
@RestController
@RequestMapping("/educms/bannerAdmin")
//@CrossOrigin
public class BannerAdminController {

    @Autowired
    private CrmBannerService crmBannerService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping("/pageBanner/{page}/{limit}")
    public Result pageBanner(@PathVariable long page,@PathVariable long limit){
        return crmBannerService.pageBanner(page,limit);
    }

    @PostMapping("/addBanner")
    public Result addBanner(@RequestBody CrmBanner banner){
        boolean save = crmBannerService.save(banner);
        if (!save) {
            return Result.error("保存错误！");
        }
        stringRedisTemplate.delete("IndexBanner");
        return Result.ok();
    }
    @GetMapping("/getBanner/{id}")
    public Result getBanner(@PathVariable String id){
        CrmBanner crmBanner = crmBannerService.getById(id);
        return Result.ok(crmBanner);
    }
    @PutMapping("/updateBanner")
    public Result updateBanner(@RequestBody CrmBanner banner) {
        boolean update = crmBannerService.updateById(banner);
        if (!update) {
            return Result.error("修改错误!");
        }
        stringRedisTemplate.delete("IndexBanner");
        return Result.ok();
    }
    @DeleteMapping("/deleteBanner/{id}")
    public Result deleteBanner(@PathVariable String id){
        boolean remove = crmBannerService.removeById(id);
        if (!remove) {
            return Result.error("删除失败");
        }
        stringRedisTemplate.delete("IndexBanner");
        return Result.ok();
    }
}

