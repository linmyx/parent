package com.linmyx.service_edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.linmyx.commonUtils.Result;

import com.linmyx.service_edu.entity.EduChapter;
import com.linmyx.service_edu.entity.EduVideo;
import com.linmyx.service_edu.entity.chapter.ChapterVo;
import com.linmyx.service_edu.entity.chapter.VideoVo;
import com.linmyx.service_edu.mapper.EduChapterMapper;
import com.linmyx.service_edu.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linmyx.service_edu.service.EduVideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-11-08
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Resource
    private EduVideoService videoService;
    /**
     * 根据课程id查询课程
     * @param courseId
     * @return
     */
    @Override
    public Result getChapterVideo(String courseId) {
        QueryWrapper<EduChapter> queryWrapperChapter = new QueryWrapper<>();
        queryWrapperChapter.eq("course_id",courseId);
        List<EduChapter> listEduChapter = list(queryWrapperChapter);
        if (listEduChapter.size() == 0){
            return Result.ok();
        }

        QueryWrapper<EduVideo> queryWrapperEduVideo=new QueryWrapper<>();

        queryWrapperEduVideo.eq("course_id",courseId);
        List<EduVideo> listEduVideo = videoService.list(queryWrapperEduVideo);
        List<ChapterVo> findList = new ArrayList<>();
        for (int i=0; i < listEduChapter.size();i++){
            EduChapter eduChapter = listEduChapter.get(i);
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter, chapterVo);
            List<VideoVo> children=new ArrayList<>();
            for (int j = 0; j < listEduVideo.size(); j++) {
                EduVideo eduVideo = listEduVideo.get(j);
                VideoVo videoVo = new VideoVo();
                BeanUtils.copyProperties(eduVideo,videoVo);
                if(eduChapter.getId().equals(eduVideo.getChapterId())){
                    children.add(videoVo);
                }
                chapterVo.setChildren(children);
            }
            findList.add(chapterVo);
        }
        return Result.ok(findList);
    }

    @Override
    public Result updateChapter(EduChapter eduChapter) {
        boolean update = updateById(eduChapter);
        if (!update) {
            return Result.error("修改章节错误");
        }
        return Result.ok();
    }

    @Override
    public Result getChapterInfo(String chapterId) {
        EduChapter eduChapter = getById(chapterId);
        return Result.ok(eduChapter);
    }

    /**
     * 删除章节的方法
     * @param chapterId
     * @return
     */
    @Override
    public Result deleteChapter(String chapterId) {
        //先查询章节id里有没有小结
        QueryWrapper<EduVideo> queryWrap = new QueryWrapper<>();
        queryWrap.eq("chapter_id",chapterId);
        int count = videoService.count(queryWrap);
        if (count > 0) {
            return Result.error("请先删除章节下面的小节");
        }
        boolean remove = removeById(chapterId);
        if (!remove) {
            return Result.error("删除错误!");
        }
        return Result.ok();
    }

    @Override
    public void removeCourseByChapter(String courseId) {
        QueryWrapper<EduChapter> queryWrap=new QueryWrapper();
        queryWrap.eq("course_id",courseId);
        remove(queryWrap);
    }

    @Override
    public List<ChapterVo> getCourseVideoById(String courseId){
        QueryWrapper<EduChapter> queryWrapperChapter = new QueryWrapper<>();
        queryWrapperChapter.eq("course_id",courseId);
        List<EduChapter> listEduChapter = list(queryWrapperChapter);
        if (listEduChapter.size() == 0){
            throw new RuntimeException("获取章节失败！！");
        }

        QueryWrapper<EduVideo> queryWrapperEduVideo=new QueryWrapper<>();

        queryWrapperEduVideo.eq("course_id",courseId);
        List<EduVideo> listEduVideo = videoService.list(queryWrapperEduVideo);
        List<ChapterVo> findList = new ArrayList<>();
        for (int i=0; i < listEduChapter.size();i++){
            EduChapter eduChapter = listEduChapter.get(i);
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter, chapterVo);
            List<VideoVo> children=new ArrayList<>();
            for (int j = 0; j < listEduVideo.size(); j++) {
                EduVideo eduVideo = listEduVideo.get(j);
                VideoVo videoVo = new VideoVo();
                BeanUtils.copyProperties(eduVideo,videoVo);
                if(eduChapter.getId().equals(eduVideo.getChapterId())){
                    children.add(videoVo);
                }
                chapterVo.setChildren(children);
            }
            findList.add(chapterVo);
        }
        return findList;
    }

}
