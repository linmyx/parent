package com.linmyx.service_edu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.linmyx.commonUtils.Result;
import com.linmyx.service_edu.entity.EduTeacher;
import com.linmyx.service_edu.entity.vo.TeacherVo;
import com.linmyx.service_edu.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-11-05
 */
@Api("讲师管理")
@RestController
@RequestMapping("/eduservice/teacher")
//@CrossOrigin   //解决跨域问题
public class EduTeacherController {

    @Autowired
    private EduTeacherService eduTeacherService;

    @ApiOperation("所有讲师列表")
    @GetMapping("/findAll")
    public Result findAll(){
        List<EduTeacher> list = eduTeacherService.list(null);
        return Result.ok(list);
    }
    @ApiOperation("逻辑删除讲师")
    @DeleteMapping("/{id}")
    public Result deleteTeacherById(@PathVariable("id") String id){
        boolean b = eduTeacherService.removeById(id);
        if (!b) {
            return Result.error("删除失败!");
        }
        return Result.ok();
    }
    /**
     * 分页功能
     */
    @GetMapping("/pageTeacher/{current}/{limit}")
    public Result pageInfo(@PathVariable long current,@PathVariable long limit ){
        Page<EduTeacher> pageInfo=new Page<>(current,limit);
        eduTeacherService.page(pageInfo, null);
        long total = pageInfo.getTotal();
        List<EduTeacher> records = pageInfo.getRecords();
        Map<String,Object> map=new HashMap<>();
        map.put("total",total);
        map.put("rows",records);
        return Result.ok(map);
    }

    /**
     * 条件分页查询
     * @param current
     * @param limit
     * @param teacherVo
     * @return
     */
    @PostMapping ("/pageTeacherCondition/{current}/{limit}")
    public Result pageTeacherCondition(@PathVariable long current,@PathVariable long limit,
                                     @RequestBody(required = false) TeacherVo teacherVo){

        return eduTeacherService.pageByQuery(current,limit,teacherVo);
    }

    /**
     * 添加讲师
     * @param eduTeacher
     * @return
     */
    @PostMapping("/addTeacher")
    public Result addTeacher(@RequestBody EduTeacher eduTeacher){
        boolean save = eduTeacherService.save(eduTeacher);
        if (!save){
            return Result.error("添加失败");
        }
        return Result.ok();
    }

    /**
     * 根据id查找讲师
     */
    @GetMapping("/getTeacher/{id}")
    public Result getTeacher(@PathVariable String id){
        EduTeacher eduTeacher = eduTeacherService.getById(id);
        return Result.ok(eduTeacher);
    }

    /**
     * 修改讲师信息
     * @param eduTeacher
     * @return
     */
    @PostMapping("/updateTeacher")
    public Result updateTeacher(@RequestBody EduTeacher eduTeacher){
        boolean flag = eduTeacherService.updateById(eduTeacher);
        if (!flag) {
            return Result.error("修改失败");
        }
        return Result.ok();
    }

}

