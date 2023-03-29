package com.linmyx.service_edu.service;

import com.linmyx.commonUtils.Result;
import com.linmyx.service_edu.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.linmyx.service_edu.entity.vo.TeacherVo;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-11-05
 */
public interface EduTeacherService extends IService<EduTeacher> {

    Result pageByQuery(long current, long limit, TeacherVo teacherVo);

    Result getTeacherFrontList(long page, long limit);

    Result getTeacherInfo(String id);
}
