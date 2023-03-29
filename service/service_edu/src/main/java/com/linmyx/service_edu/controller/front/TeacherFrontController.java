package com.linmyx.service_edu.controller.front;

import com.linmyx.commonUtils.Result;
import com.linmyx.service_edu.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/eduservice/teacherFront")
//@CrossOrigin
public class TeacherFrontController {

    @Autowired
    private EduTeacherService teacherService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @PostMapping("/getTeacherFrontList/{page}/{limit}")
    public Result getTeacherFrontList(@PathVariable long page,@PathVariable long limit){
        return teacherService.getTeacherFrontList(page,limit);
    }

    @GetMapping("/getTeacherInfo/{id}")
    public Result getTeacherInfo(@PathVariable String id){
        return teacherService.getTeacherInfo(id);
    }

    @GetMapping("/redisTest")
    public Result redisTest(){
        stringRedisTemplate.opsForValue().set("name","test");
        return Result.ok();
    }

}
