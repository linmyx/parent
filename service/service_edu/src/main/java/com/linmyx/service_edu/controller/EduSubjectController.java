package com.linmyx.service_edu.controller;


import com.linmyx.commonUtils.Result;
import com.linmyx.service_edu.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-11-07
 */
@RestController
@RequestMapping("/eduservice/subject")
//@CrossOrigin
public class EduSubjectController {
    @Resource
    private EduSubjectService eduSubjectService;

    //添加课程分类
    //获取上传过来的文件，获取文件内容读取出来
    @PostMapping("/addSubject")
    public Result addSubject(MultipartFile file){
        //上传过来的文件
        eduSubjectService.saveSubject(file,eduSubjectService);
        return Result.ok();
    }

    //获取所有课程分类数据(树形结构)
    @GetMapping("/getAllSubject")
    public Result getAllSubject(){
        return eduSubjectService.getAllSubject();
    }

    //根据一级分类查询二级分类

}

