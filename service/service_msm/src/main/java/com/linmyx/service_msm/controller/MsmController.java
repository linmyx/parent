package com.linmyx.service_msm.controller;

import com.linmyx.commonUtils.Result;
import com.linmyx.service_msm.service.MsmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/eduMsm/msm")
//@CrossOrigin
public class MsmController {

    @Autowired
    private MsmService msmService;

    @GetMapping("/send/{phone}")
    public Result send(@PathVariable String phone){
        return msmService.send(phone);
    }
}
