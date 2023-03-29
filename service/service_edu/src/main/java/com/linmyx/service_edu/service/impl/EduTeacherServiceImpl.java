package com.linmyx.service_edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.linmyx.commonUtils.Result;
import com.linmyx.service_edu.entity.EduCourse;
import com.linmyx.service_edu.entity.EduTeacher;
import com.linmyx.service_edu.entity.vo.TeacherVo;
import com.linmyx.service_edu.mapper.EduTeacherMapper;
import com.linmyx.service_edu.service.EduCourseService;
import com.linmyx.service_edu.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-11-05
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    @Autowired
    private EduCourseService courseService;

    @Override
    public Result pageByQuery(long current, long limit, TeacherVo teacherVo) {
        Page<EduTeacher> pageInfo = new Page<>(current,limit);
        //构造条件
        QueryWrapper<EduTeacher> queryWrap = new QueryWrapper<>();
        String name = teacherVo.getName();
        Integer level = teacherVo.getLevel();
        String begin = teacherVo.getBegin();
        String end = teacherVo.getEnd();
        //判断条件之是否为空
        if (!StringUtils.isEmpty(name)){
            queryWrap.like("name",name);
        }
        if (!StringUtils.isEmpty(level)) {
            queryWrap.eq("level",level);
        }
        if (!StringUtils.isEmpty(begin)) {
            queryWrap.ge("gmt_create",begin);
        }
        if (!StringUtils.isEmpty(end)) {
            queryWrap.le("gmt_modified",end);
        }
        queryWrap.orderByDesc("gmt_create");
        //分页查询
        IPage<EduTeacher> page =page(pageInfo, queryWrap);
//        long total = page.getTotal();
//        List<EduTeacher> records = page.getRecords();
//        Map<String,Object> map=new HashMap<>();
//        map.put("total",total);
//        map.put("rows",records);
        return Result.ok(page);
    }


    /**
     * 前端获取teacher
     * @param page
     * @param limit
     * @return
     */
    @Override
    public Result getTeacherFrontList(long page, long limit) {
        Page<EduTeacher> pageInfo=new Page<>(page,limit);
        QueryWrapper<EduTeacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        page(pageInfo, queryWrapper);

        List<EduTeacher> records = pageInfo.getRecords();

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

    /**
     * 根据id查询讲师的课程
     * @param id
     * @return
     */
    @Override
    public Result getTeacherInfo(String id) {
        QueryWrapper<EduTeacher> queryWrap=new QueryWrapper<>();
        queryWrap.eq("id", id);
        EduTeacher teacher = getOne(queryWrap);
        List<EduCourse> teacherCourse = courseService.getTeacherCourseById(id);
        Map<String,Object> map=new HashMap<>();
        map.put("teacher",teacher);
        map.put("course",teacherCourse);
        return Result.ok(map);
    }
}
