package com.linmyx.service_ucenter.service;

import com.linmyx.commonUtils.Result;
import com.linmyx.service_ucenter.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.linmyx.service_ucenter.entity.vo.LoginVo;
import com.linmyx.service_ucenter.entity.vo.RegisterVo;
import com.linmyx.service_ucenter.entity.vo.UserQuery;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-11-11
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    Result login(LoginVo loginVo);

    Result register(RegisterVo registerVo);

    UcenterMember getOpenid(String openid);

    Result countRegister(String day);

    Result getAllUserPage(long page, long limit, UserQuery userQuery);
}
