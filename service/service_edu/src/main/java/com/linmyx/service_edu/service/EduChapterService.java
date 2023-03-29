package com.linmyx.service_edu.service;

import com.linmyx.commonUtils.Result;
import com.linmyx.service_edu.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.linmyx.service_edu.entity.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-11-08
 */
public interface EduChapterService extends IService<EduChapter> {

    Result getChapterVideo(String courseId);

    Result updateChapter(EduChapter eduChapter);

    Result getChapterInfo(String chapterId);

    Result deleteChapter(String chapterId);

    void removeCourseByChapter(String courseId);

    public List<ChapterVo> getCourseVideoById(String courseId);
}
