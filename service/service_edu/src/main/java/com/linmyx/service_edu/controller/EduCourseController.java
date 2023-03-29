package com.linmyx.service_edu.controller;


import com.linmyx.commonUtils.Result;
import com.linmyx.service_edu.entity.EduCourse;
import com.linmyx.service_edu.entity.vo.CourseQuery;
import com.linmyx.service_edu.entity.vo.CourseVo;
import com.linmyx.service_edu.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-11-08
 */
@RestController
@RequestMapping("/eduservice/course")
//@CrossOrigin
public class EduCourseController {

    @Autowired
    private EduCourseService courseService;

    //课程列表
    //TODO 完善条件查询带分页
    @PostMapping("/getCourseListPage/{page}/{limit}")
    public Result getCourseListPage(@PathVariable long page, @PathVariable long limit, @RequestBody(required = false) CourseQuery courseQuery){

        return courseService.getCourseListPage(page,limit,courseQuery);
    }

    /**
     * 删除课程相关的所有数据
     * @return
     */
    @DeleteMapping("/deleteCourseById/{courseId}")
    public Result deleteCourseById(@PathVariable String courseId){
        return courseService.deleteCourseById(courseId);
    }




    @PostMapping("/addCourseInfo")
    public Result addCourseInfo(@RequestBody CourseVo courseVo){
        return courseService.savaCourseInfo(courseVo);
    }

    /**
     * 根据id查询课程信息
     * @param courseId
     * @return
     */
    @GetMapping("/getCourseInfo/{courseId}")
    public Result<CourseVo> getCourseInfo(@PathVariable String courseId){
        return courseService.getCourseInfo(courseId);
    }
    /**
     * 修改课程信息
     */
    @PostMapping("/updateCourseInfo")
    public Result updateCourseInfo(@RequestBody CourseVo courseVo){
        return courseService.updateCourseInfo(courseVo);
    }
    //根据课程id查询课程确认信息
    @GetMapping("/getPublishCourseInfo/{courseId}")
    public Result getPublishCourseInfo(@PathVariable String courseId){
        return courseService.getPublishCourseInfo(courseId);
    }

    //修改课程发布状态
    @PostMapping("/updateCoursePublish/{courseId}")
    public Result updateCoursePublish(@PathVariable String courseId){
        EduCourse eduCourse=new EduCourse();
        eduCourse.setId(courseId);
        eduCourse.setStatus("Normal");
        courseService.updateById(eduCourse);
        return Result.ok();
    }

    //根据id修改课程状态
    @PutMapping("/updateCourseStatus/{courseId}")
    public Result updateCourseStatus(@PathVariable String courseId){
        return courseService.updateCourseStatus(courseId);
    }
}

