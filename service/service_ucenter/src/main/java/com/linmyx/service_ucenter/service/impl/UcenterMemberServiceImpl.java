package com.linmyx.service_ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.linmyx.commonUtils.JwtUtils;
import com.linmyx.commonUtils.Result;

import com.linmyx.service_ucenter.entity.UcenterMember;
import com.linmyx.service_ucenter.entity.vo.LoginVo;
import com.linmyx.service_ucenter.entity.vo.RegisterVo;
import com.linmyx.service_ucenter.entity.vo.UserQuery;
import com.linmyx.service_ucenter.mapper.UcenterMemberMapper;
import com.linmyx.service_ucenter.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linmyx.service_ucenter.utils.MD5;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-11-11
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public Result login(LoginVo loginVo) {
        //获取登录手机号或者密码
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)){
            return Result.error("账号或者密码为空");
        }
        //判断手机号是否正确
        QueryWrapper<UcenterMember> queryWrap=new QueryWrapper<>();
        queryWrap.eq("mobile",mobile);
        UcenterMember mobileMember = getOne(queryWrap);
        if (mobileMember == null){
            return Result.error("账号不存在!!");
        }
        String encrypt = MD5.encrypt(password);
        //判断密码
        if (!mobileMember.getPassword().equals(encrypt)){
            return Result.error("账号或者密码错误!!");
        }
        //判断用户是否被禁用
        if (mobileMember.getIsDisabled()){
            return Result.error("账号已被禁用!!");
        }
        String jwtToken = JwtUtils.getJwtToken(mobileMember.getId(), mobileMember.getNickname());
        String key="login:user"+mobileMember.getId();
        stringRedisTemplate.opsForValue().set(key,jwtToken,30, TimeUnit.MINUTES);
        return Result.ok(jwtToken);
    }

    @Override
    public Result register(RegisterVo registerVo) {

        String code = registerVo.getCode();
        String mobile = registerVo.getMobile();
        String password = registerVo.getPassword();
        String nickname = registerVo.getNickname();
        if(StringUtils.isEmpty(mobile) ||
                StringUtils.isEmpty(mobile) ||
                StringUtils.isEmpty(password) ||
                StringUtils.isEmpty(code)) {
            throw new RuntimeException("注册失败！");
        }
        QueryWrapper<UcenterMember> queryWrap=new QueryWrapper<>();
        queryWrap.eq("mobile",mobile);
        int count = count(queryWrap);
        if (count > 0) {
            throw new RuntimeException("账号已经存在！");
        }
        String redisCode = stringRedisTemplate.opsForValue().get(mobile);
        if (!redisCode.equals(code)){
            throw new RuntimeException("验证码错误！");
        }
        String MD5Password = MD5.encrypt(password);
        UcenterMember ucenterMember = new UcenterMember();

        ucenterMember.setMobile(mobile);
        ucenterMember.setNickname(nickname);
        ucenterMember.setIsDisabled(false);
        ucenterMember.setPassword(MD5Password);
        boolean save = save(ucenterMember);
        if (save) {
            stringRedisTemplate.delete(mobile);
        }
        return Result.ok();
    }


    @Override
    public UcenterMember getOpenid(String openid) {
        QueryWrapper<UcenterMember> queryWrap=new QueryWrapper<>();
        queryWrap.eq("openid",openid);
        UcenterMember ucenterMember = getOne(queryWrap);
        return ucenterMember;
    }

    /**
     * 统计某一天的注册人数
     * @param day
     * @return
     */
    @Override
    public Result countRegister(String day) {
        Integer integer = baseMapper.countRegsterDay(day);
        return Result.ok(integer);
    }

    /**
     * 后端获取所有的会员信息
     * @param page
     * @param limit
     * @param userQuery
     * @return
     */
    @Override
    public Result getAllUserPage(long page, long limit, UserQuery userQuery) {
        Page<UcenterMember> userInfo=new Page<>(page,limit);

        QueryWrapper<UcenterMember> queryWrap=new QueryWrapper<>();

        if (!StringUtils.isEmpty(userQuery.getMobile())){
            queryWrap.eq("mobile",userQuery.getMobile());
        }
        if (userQuery.getSex()>0){
            queryWrap.eq("sex",userQuery.getSex());
        }
        if (!StringUtils.isEmpty(userQuery.getBegin()) && !StringUtils.isEmpty(userQuery.getEnd())){
            queryWrap.between("gmt_create",userQuery.getBegin(),userQuery.getEnd());
        }
        queryWrap.select("mobile","nickname","sex","age","is_disabled","gmt_create","gmt_modified");
        page(userInfo,queryWrap);
        Map<String,Object> userMap=new HashMap<>();
        userMap.put("total",userInfo.getTotal());
        userMap.put("rows",userInfo.getRecords());
        return Result.ok(userMap);
    }
}
