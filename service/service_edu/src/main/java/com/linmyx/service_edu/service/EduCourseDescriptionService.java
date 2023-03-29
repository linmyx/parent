package com.linmyx.service_edu.service;

import com.linmyx.service_edu.entity.EduCourseDescription;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程简介 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-11-08
 */
public interface EduCourseDescriptionService extends IService<EduCourseDescription> {

    void removeCourseByDescription(String courseId);

}
