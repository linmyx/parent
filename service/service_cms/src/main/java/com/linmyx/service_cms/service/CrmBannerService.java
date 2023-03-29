package com.linmyx.service_cms.service;

import com.linmyx.commonUtils.Result;
import com.linmyx.service_cms.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.IOException;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-11-11
 */
public interface CrmBannerService extends IService<CrmBanner> {

    Result getAllBanner();

    Result pageBanner(long page, long limit);
}
