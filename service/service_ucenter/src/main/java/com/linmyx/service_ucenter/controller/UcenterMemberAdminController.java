package com.linmyx.service_ucenter.controller;

import com.linmyx.commonUtils.Result;
import com.linmyx.service_ucenter.entity.vo.UserQuery;
import com.linmyx.service_ucenter.service.UcenterMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/educenter/member/admin")
public class UcenterMemberAdminController {

    @Autowired
    private UcenterMemberService memberService;

    @PostMapping("/getAllUserPage/{page}/{limit}")
    public Result getAllUserPage(@PathVariable long page, @PathVariable long limit,
                                 @RequestBody(required = false) UserQuery userQuery){
        return memberService.getAllUserPage(page,limit,userQuery);
    }

}
