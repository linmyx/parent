package com.linmyx.service_edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.util.BeanUtil;
import com.linmyx.commonUtils.JwtUtils;
import com.linmyx.commonUtils.Result;
import com.linmyx.service_edu.client.OrderClient;
import com.linmyx.service_edu.client.VodClient;
import com.linmyx.service_edu.entity.*;
import com.linmyx.service_edu.entity.chapter.ChapterVo;
import com.linmyx.service_edu.entity.vo.CoursePublishVo;
import com.linmyx.service_edu.entity.vo.CourseQuery;
import com.linmyx.service_edu.entity.vo.CourseVo;
import com.linmyx.service_edu.entity.vo.frontvo.CourseFrontVo;
import com.linmyx.service_edu.entity.vo.frontvo.CourseWebVo;
import com.linmyx.service_edu.mapper.EduCourseMapper;
import com.linmyx.service_edu.service.EduChapterService;
import com.linmyx.service_edu.service.EduCourseDescriptionService;
import com.linmyx.service_edu.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linmyx.service_edu.service.EduVideoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-11-08
 */
@Service
@Slf4j
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionService courseDescriptionService;
    @Autowired
    private EduChapterService eduChapterService;
    @Autowired
    private EduVideoService eduVideoService;
    @Autowired
    private VodClient vodClient;

    @Autowired
    private OrderClient orderClient;

    /**
     * 添加课程信息
     * @param courseVo
     * @return
     */
    @Override
    @Transactional
    public Result savaCourseInfo(CourseVo courseVo) {
        //1.向课程表条件课程基本信息
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseVo,eduCourse);
        boolean save = save(eduCourse);
        if (!save) {
            return Result.error("添加课程失败");
        }
        //获取添加之后的id

        //2.向课程简介表添加课程简介
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        BeanUtils.copyProperties(courseVo, eduCourseDescription);
        eduCourseDescription.setId(eduCourse.getId());
        boolean add = courseDescriptionService.save(eduCourseDescription);
        if (!add) {
            return Result.error("添加课程失败");
        }

        return Result.ok(eduCourse.getId());
    }

    /**
     * 根据id查询课程的基本信息
     * @param courseId
     * @return
     */
    @Override
    public Result<CourseVo> getCourseInfo(String courseId) {
        EduCourse eduCourse = getById(courseId);
        EduCourseDescription courseDescription = courseDescriptionService.getById(courseId);
        CourseVo courseVo = new CourseVo();
        BeanUtils.copyProperties(eduCourse, courseVo);
        BeanUtils.copyProperties(courseDescription,courseVo);
        return Result.ok(courseVo);
    }
    //
    @Override
    @Transactional
    public Result updateCourseInfo(CourseVo courseVo) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseVo, eduCourse);
        EduCourseDescription courseDescription = new EduCourseDescription();
        BeanUtils.copyProperties(courseVo, courseDescription);
        boolean savaCourse = updateById(eduCourse);
        if (!savaCourse){
            return Result.error("修改失败");
        }
        boolean savaCourseDescription = courseDescriptionService.updateById(courseDescription);
        if (!savaCourseDescription) {
            return Result.error("修改失败");
        }
        return Result.ok();
    }

    /**
     * 最终发布，信息确认
     * @param courseId
     * @return
     */
    @Override
    public Result getPublishCourseInfo(String courseId) {
        CoursePublishVo publishCourseInfo = baseMapper.getPublishCourseInfo(courseId);
        return Result.ok(publishCourseInfo);
    }

    @Override
    @Transactional
    public Result deleteCourseById(String courseId) {
        eduVideoService.removeCourseByVideo(courseId);
        //2.删除章节内容
        eduChapterService.removeCourseByChapter(courseId);
        //3.删除简介内容
       courseDescriptionService.removeCourseByDescription(courseId);
        //4.删除课程内容
        removeById(courseId);
        return Result.ok();
    }

    /**
     * 根据讲师id，查询讲师的课程
     * @param teacherId
     * @return
     */
    @Override
    public List<EduCourse> getTeacherCourseById(String teacherId) {
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("teacher_id",teacherId);
        queryWrapper.orderByDesc("gmt_modified");
        List<EduCourse> courseList = list(queryWrapper);
        return courseList;
    }

    /**
     * 前端查询课程分页显示带条件查询
     * @param page
     * @param limit
     * @param courseFrontVo
     * @return
     */
    @Override
    public Result getFrontCoursePage(long page, long limit, CourseFrontVo courseFrontVo) {
        Page<EduCourse> pageInfo=new Page<>(page,limit);
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();

        if (!StringUtils.isEmpty(courseFrontVo.getTitle())){
            queryWrapper.eq("title",courseFrontVo.getTitle());
        }
        if (!StringUtils.isEmpty(courseFrontVo.getSubjectParentId())) {
            queryWrapper.eq("subject_parent_id",courseFrontVo.getSubjectParentId());
        }
        if (!StringUtils.isEmpty(courseFrontVo.getSubjectId())) {
            queryWrapper.eq("subject_id",courseFrontVo.getSubjectId());
        }

        if (!StringUtils.isEmpty(courseFrontVo.getBuyCountSort())) {
            queryWrapper.orderByDesc("buy_count");
        }
        if (!StringUtils.isEmpty(courseFrontVo.getGmtCreateSort())) {
            queryWrapper.orderByDesc("gmt_modified");
        }
        if (!StringUtils.isEmpty(courseFrontVo.getPriceSort())) {
            queryWrapper.orderByAsc("price");
        }

         page(pageInfo,queryWrapper);

        List<EduCourse> records = pageInfo.getRecords();

        long current = pageInfo.getCurrent();

        long pages = pageInfo.getPages();
        long size = pageInfo.getSize();
        long total = pageInfo.getTotal();
        boolean hasNext = pageInfo.hasNext();
        boolean hasPrevious = pageInfo.hasPrevious();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);
        return Result.ok(map);
    }
    //根据课程id查询课程所关联的所有的信息
    @Override
    public Result getFrontCourseInfo(String courseId, HttpServletRequest request) {
        //根据id获取课程章节
        List<ChapterVo> courseVideoById = eduChapterService.getCourseVideoById(courseId);

        CourseWebVo courseWebVo= baseMapper.getBaseCourseInfo(courseId);

        Map<String,Object> map=new HashMap<>();
        map.put("video",courseVideoById);
        map.put("course",courseWebVo);

        String userId = JwtUtils.getMemberIdByJwtToken(request);
        if (!StringUtils.isEmpty(userId)){
            //根据课程id和用户id查询当前课程是否已经购买过
            Boolean buyCourse = orderClient.isBuyCourse(courseId, userId);
            map.put("isBuy",buyCourse);
        }
        return Result.ok(map);
    }

    /**
     * 课程分页显示带条件查询
     * @param page
     * @param limit
     * @param courseQuery
     * @return
     */
    @Override
    public Result getCourseListPage(long page, long limit, CourseQuery courseQuery) {

        Page<EduCourse> courseInfo = new Page<>(page, limit);
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();

        if (!StringUtils.isEmpty(courseQuery.getTitle())){
            queryWrapper.like("title",courseQuery.getTitle());
        }
        if (!StringUtils.isEmpty(courseQuery.getStatus())) {
            queryWrapper.eq("status", courseQuery.getStatus());
        }
        page(courseInfo,queryWrapper);
        long total = courseInfo.getTotal();
        List<EduCourse> records = courseInfo.getRecords();
        Map<String, Object> map = new HashMap<>();
        map.put("records",records);
        map.put("total",total);
        return Result.ok(map);
    }

    /**
     * 根据id修改课程状态
     * @param courseId
     * @return
     */
    @Override
    public Result updateCourseStatus(String courseId) {

        EduCourse courseStatus = new EduCourse();
        courseStatus.setId(courseId);
        EduCourse eduCourse = getById(courseId);
        if (eduCourse.getStatus().equals("Normal")){
            courseStatus.setStatus("Draft");
        }
        if (eduCourse.getStatus().equals("Draft")){
            courseStatus.setStatus("Normal");
        }
        boolean update = updateById(courseStatus);
        if (!update) {
            return Result.error("修改状态失败!!");
        }
        return Result.ok();
    }
}
