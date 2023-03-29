package com.linmyx.service_edu.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.linmyx.commonUtils.Result;
import com.linmyx.service_edu.entity.EduCourse;
import com.linmyx.service_edu.entity.EduTeacher;
import com.linmyx.service_edu.service.EduCourseService;
import com.linmyx.service_edu.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/eduservice/indexFront")
//@CrossOrigin
public class IndexFrontController {
    @Autowired
    private EduCourseService eduCourseService;

    @Autowired
    private EduTeacherService eduTeacherService;



    @GetMapping("/indexCourse")
    @Cacheable(value = "Course", key = "'IndexCourse'")
    public Result indexCourse() {
        QueryWrapper<EduCourse> queryWrap=new QueryWrapper<>();
        queryWrap.orderByDesc("id");
        queryWrap.last("limit 8");
        List<EduCourse> list = eduCourseService.list(queryWrap);
        return Result.ok(list);
    }

    @GetMapping("/indexTeacher")
    @Cacheable(value = "Teacher", key = "'IndexTeacher'")
    public Result indexTeacher(){
        QueryWrapper<EduTeacher> queryWrap=new QueryWrapper<>();
        queryWrap.orderByDesc("id");
        queryWrap.last("limit 4");
        List<EduTeacher> list = eduTeacherService.list(queryWrap);
        return Result.ok(list);
    }
}
