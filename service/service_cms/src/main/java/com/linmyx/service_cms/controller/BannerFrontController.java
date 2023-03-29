package com.linmyx.service_cms.controller;


import com.linmyx.commonUtils.Result;
import com.linmyx.service_cms.service.CrmBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-11-11
 */
@RestController
@RequestMapping("/educms/bannerFront")
//@CrossOrigin
public class BannerFrontController {

    @Autowired
    private CrmBannerService crmBannerService;

    @GetMapping("/getAllBanner")
    public Result getAllBanner(){
        return crmBannerService.getAllBanner();
    }
}

