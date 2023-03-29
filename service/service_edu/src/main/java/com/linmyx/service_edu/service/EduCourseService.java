package com.linmyx.service_edu.service;

import com.linmyx.commonUtils.Result;
import com.linmyx.service_edu.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.linmyx.service_edu.entity.vo.CourseQuery;
import com.linmyx.service_edu.entity.vo.CourseVo;
import com.linmyx.service_edu.entity.vo.frontvo.CourseFrontVo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-11-08
 */
public interface EduCourseService extends IService<EduCourse> {

    Result savaCourseInfo(CourseVo courseVo);

    /**
     * 根据id查询课程信息
     * @param courseId
     * @return
     */
    Result<CourseVo> getCourseInfo(String courseId);

    Result updateCourseInfo(CourseVo courseVo);

    Result getPublishCourseInfo(String courseId);

    Result deleteCourseById(String courseId);

    List<EduCourse> getTeacherCourseById(String teacherId);

    Result getFrontCoursePage(long page, long limit, CourseFrontVo courseFrontVo);

    Result getFrontCourseInfo(String courseId, HttpServletRequest request);

    Result getCourseListPage(long page, long limit, CourseQuery courseQuery);

    Result updateCourseStatus(String courseId);
}
