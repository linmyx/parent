package com.linmyx.service_edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.linmyx.commonUtils.Result;

import com.linmyx.service_edu.client.VodClient;
import com.linmyx.service_edu.entity.EduVideo;
import com.linmyx.service_edu.mapper.EduVideoMapper;
import com.linmyx.service_edu.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-11-08
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {
    @Autowired
    private VodClient vodClient;
    @Override
    public Result getAllVideo(String chapterId) {
        QueryWrapper<EduVideo> queryWrap=new QueryWrapper<>();
        queryWrap.eq("chapter_id",chapterId);
        List<EduVideo> list = list(queryWrap);
        return Result.ok(list);
    }
    @Override
    @Transactional
    public Result deleteVideo(String videoId) {
        EduVideo video = getById(videoId);
        String videoSourceId = video.getVideoSourceId();
        if (!StringUtils.isEmpty(videoSourceId)){
            vodClient.removeAlyVideo(videoSourceId);
        }
        boolean remove = removeById(videoId);
        if (!remove) {
            return Result.error("删除失败!");
        }
        return Result.ok();
    }

    public void removeCourseByVideo(String courseId){
        //1.删除课程小节的视频
        QueryWrapper<EduVideo> eduVideoId=new QueryWrapper<>();
        eduVideoId.eq("course_id",courseId);
        eduVideoId.select("video_source_id");
        List<EduVideo> list =list(eduVideoId);
        List<String> eduVideoIds = new ArrayList<>();
        for (EduVideo eduVideo :list) {
            if (!StringUtils.isEmpty(eduVideo.getVideoSourceId())){
                eduVideoIds.add(eduVideo.getVideoSourceId());
            }
        }
        if (eduVideoIds.size() > 0) {
            Result result = vodClient.deleteBatch(eduVideoIds);
            if (result.getCode()==201){

            }
        }
        //1.根据课程id删除小节内容
        QueryWrapper<EduVideo> eduVideo=new QueryWrapper();
        eduVideo.eq("course_id",courseId);
        remove(eduVideo);
    }

}
