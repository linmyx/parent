package com.linmyx.service_edu.controller.front;

import com.linmyx.commonUtils.Result;
import com.linmyx.commonUtils.orderVo.CourseWebOrderVo;
import com.linmyx.service_edu.entity.vo.frontvo.CourseFrontVo;
import com.linmyx.service_edu.entity.vo.frontvo.CourseWebVo;
import com.linmyx.service_edu.mapper.EduCourseMapper;
import com.linmyx.service_edu.service.EduCourseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/eduservice/courseFront/")
//@CrossOrigin
public class CourseFrontController {

    @Autowired
    private EduCourseService courseService;
    @Autowired
    private EduCourseMapper courseMapper;

    @PostMapping("/getFrontCoursePage/{page}/{limit}")
    public Result getFrontCoursePage(@PathVariable long page,@PathVariable long limit,@RequestBody(required = false) CourseFrontVo courseFrontVo){
        return courseService.getFrontCoursePage(page,limit,courseFrontVo);
    }

    @GetMapping("/getFrontCourseInfo/{courseId}")
    public Result getFrontCourseInfo(@PathVariable String courseId,HttpServletRequest request){
        return courseService.getFrontCourseInfo(courseId,request);
    }

    /**
     * 根据课程id查询课程信息
     */
    @PostMapping("/getCourseInfoOrder/{courseId}")
    public CourseWebOrderVo getCourseInfoOrder(@PathVariable String courseId){
        CourseWebVo baseCourseInfo = courseMapper.getBaseCourseInfo(courseId);
        CourseWebOrderVo courseWebOrderVo=new CourseWebOrderVo();
        BeanUtils.copyProperties(baseCourseInfo,courseWebOrderVo);
        return courseWebOrderVo;
    }

}
