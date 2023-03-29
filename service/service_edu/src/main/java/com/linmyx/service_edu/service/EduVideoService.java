package com.linmyx.service_edu.service;

import com.linmyx.commonUtils.Result;
import com.linmyx.service_edu.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-11-08
 */
public interface EduVideoService extends IService<EduVideo> {

    Result getAllVideo(String chapterId);

    Result deleteVideo(String videoId);
    void removeCourseByVideo(String courseId);
}
