package com.linmyx.service_edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.linmyx.service_edu.entity.EduCourseDescription;
import com.linmyx.service_edu.mapper.EduCourseDescriptionMapper;
import com.linmyx.service_edu.service.EduCourseDescriptionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程简介 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-11-08
 */
@Service
public class EduCourseDescriptionServiceImpl extends ServiceImpl<EduCourseDescriptionMapper, EduCourseDescription> implements EduCourseDescriptionService {

    @Override
    public void removeCourseByDescription(String courseId) {
        QueryWrapper<EduCourseDescription> descriptionQueryWrapper=new QueryWrapper<>();
        descriptionQueryWrapper.eq("id",courseId);
        remove(descriptionQueryWrapper);
    }
}
