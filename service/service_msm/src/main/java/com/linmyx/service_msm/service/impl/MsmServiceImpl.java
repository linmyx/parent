package com.linmyx.service_msm.service.impl;

import com.linmyx.commonUtils.Result;
import com.linmyx.service_msm.service.MsmService;
import com.linmyx.service_msm.utils.MsmSend;
import com.linmyx.service_msm.utils.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class MsmServiceImpl implements MsmService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 发送短信的方法
     * @param phone
     * @return
     */
    @Override
    public Result send(String phone) {

        String code = RandomUtil.getFourBitRandom();
        Boolean aBoolean = MsmSend.sendClient(phone, code);
        if (!aBoolean) {
            return Result.error("验证码发送失败!");
        }
        stringRedisTemplate.opsForValue().set(phone,code,5, TimeUnit.MINUTES);
        log.info("验证码是:{}",code);
        return Result.ok(code);
    }
}
