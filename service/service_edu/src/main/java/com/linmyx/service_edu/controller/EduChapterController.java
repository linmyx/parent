package com.linmyx.service_edu.controller;


import com.linmyx.commonUtils.Result;
import com.linmyx.service_edu.entity.EduChapter;
import com.linmyx.service_edu.service.EduChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-11-08
 */
@RestController
@RequestMapping("/eduservice/chapter")
//@CrossOrigin
public class EduChapterController {
    @Autowired
    private EduChapterService chapterService;

    @GetMapping("/getChapterVideo/{courseId}")
    public Result getChapterVideo(@PathVariable String courseId){
        return chapterService.getChapterVideo(courseId);
    }

    /**
     * 添加章节
     */
    @PostMapping("/addChapter")
    public Result addChapter(@RequestBody EduChapter eduChapter){
        chapterService.save(eduChapter);
        return Result.ok();
    }
    /**
     * 根据id查询章节
     */
    @GetMapping("/getChapterInfo/{chapterId}")
    public Result getChapterInfo(@PathVariable String chapterId){
        return chapterService.getChapterInfo(chapterId);
    }

    /**
     * 修改章节
     */
    @PostMapping("/updateChapter")
    public Result updateChapter(@RequestBody EduChapter eduChapter){
        return chapterService.updateChapter(eduChapter);
    }
    /**
     * 根据id删除章节
     */
    @DeleteMapping("/deleteChapter/{chapterId}")
    public Result deleteChapter(@PathVariable String chapterId) {
        return chapterService.deleteChapter(chapterId);
    }
}

