package com.linmyx.service_edu.controller;


import com.linmyx.commonUtils.Result;
import com.linmyx.service_edu.entity.EduVideo;
import com.linmyx.service_edu.service.EduVideoService;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-11-08
 */
@RestController
@RequestMapping("/eduservice/video")
//@CrossOrigin   //解决跨域问题
public class EduVideoController {
    @Autowired
    private EduVideoService videoService;

    /**
     * 根据章节id获取所有小章节
     */
    @GetMapping("/getAllVideo/{chapterId}")
    public Result getAllVideo(@PathVariable String chapterId){
        return videoService.getAllVideo(chapterId);
    }
    /**
     * 添加小节
     */
    @PostMapping("/addVideo")
    public Result addVideo(@RequestBody EduVideo eduVideo){
        boolean save = videoService.save(eduVideo);
        if (!save) {
            return Result.error("添加失败");
        }
        return Result.ok();
    }
    /**
     * 删除小节
     *
     * 未完成
     */
    @DeleteMapping("/deleteVideo/{videoId}")
    public Result deleteVideo(@PathVariable String videoId){

        return videoService.deleteVideo(videoId);
    }

    /**
     * 根据id获取小节课程
     * @param videoId
     * @return
     */

    @GetMapping("/getVideoInfo/{videoId}")
    public Result getVideoInfo(@PathVariable String videoId){
        EduVideo video = videoService.getById(videoId);
        return Result.ok(video);
    }

    /**
     * 修改小节课程信息
     * @param eduVideo
     * @return
     */
    @PostMapping("/updateVideo")
    public Result updateVideo(@RequestBody EduVideo eduVideo){
        boolean update = videoService.updateById(eduVideo);
        if (!update) {
            return Result.error("修改失败");
        }
        return Result.ok();
    }
}

