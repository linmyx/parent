package com.linmyx.service_ucenter.controller;


import com.linmyx.commonUtils.JwtUtils;
import com.linmyx.commonUtils.Result;
import com.linmyx.commonUtils.orderVo.UcenterMemberOrder;
import com.linmyx.service_ucenter.entity.UcenterMember;
import com.linmyx.service_ucenter.entity.vo.LoginVo;
import com.linmyx.service_ucenter.entity.vo.RegisterVo;
import com.linmyx.service_ucenter.service.UcenterMemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-11-11
 */
@RestController
@RequestMapping("/educenter/member")
//@CrossOrigin
public class UcenterMemberController {
    @Autowired
    private UcenterMemberService memberService;

    @PostMapping("/login")
    public Result login(@RequestBody LoginVo loginVo){
        return memberService.login(loginVo);
    }

    @PostMapping("/register")
    public Result register(@RequestBody RegisterVo registerVo){
        return memberService.register(registerVo);
    }

    //根据token获取用户信息
    @GetMapping("/getMemberInfo")
    public Result getMemberInfo(HttpServletRequest request){
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        //查询数据库根据用户id获取用户信息
        UcenterMember memberMember = memberService.getById(memberId);
        return Result.ok(memberMember);
    }

    /**
     * 根据用户id获取用户信息
     */
    @PostMapping("/getUserInfo/{id}")
    public UcenterMember getUserInfo(@PathVariable String id){
        UcenterMember ucenterMember = memberService.getById(id);
        return ucenterMember;
    }

    /**
     * 订单，根据id获取用户信息
     */
    @PostMapping("/getUserInfoByOrder/{id}")
    public UcenterMemberOrder getUserInfoByOrder(@PathVariable String id){
        UcenterMember ucenterMember = memberService.getById(id);
        UcenterMemberOrder ucenterMemberOrder = new UcenterMemberOrder();
        BeanUtils.copyProperties(ucenterMember, ucenterMemberOrder);
        return ucenterMemberOrder;
    }

    /**
     * 查询某一天的注册成人数
     */
    @GetMapping("/countRegister/{day}")
    public Result countRegister(@PathVariable String day){
        return memberService.countRegister(day);
    }

}

