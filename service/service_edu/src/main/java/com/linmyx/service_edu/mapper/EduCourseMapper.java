package com.linmyx.service_edu.mapper;

import com.linmyx.service_edu.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.linmyx.service_edu.entity.vo.CoursePublishVo;
import com.linmyx.service_edu.entity.vo.frontvo.CourseWebVo;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2022-11-08
 */
@Mapper
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    public CoursePublishVo getPublishCourseInfo(String courseId);


    CourseWebVo getBaseCourseInfo(String courseId);
}
