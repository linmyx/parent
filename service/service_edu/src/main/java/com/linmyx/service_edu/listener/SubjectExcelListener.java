package com.linmyx.service_edu.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.linmyx.service_edu.entity.EduSubject;
import com.linmyx.service_edu.excel.SubjectData;
import com.linmyx.service_edu.service.EduSubjectService;


public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {

    public EduSubjectService eduSubjectService;

    public SubjectExcelListener(){

    }

    public SubjectExcelListener(EduSubjectService eduSubjectService){
        this.eduSubjectService = eduSubjectService;
    }
    //读取excel内容
    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if (subjectData == null){
           throw new NullPointerException("文件数据为空");
        }
        //判断一级分类
        EduSubject existEduSubject = existEduSubject(eduSubjectService, subjectData.getOneSubjectName());
        if (existEduSubject == null) {  //没有相同的一级分类
            EduSubject eduSubject = new EduSubject();
            eduSubject.setParentId("0");
            eduSubject.setTitle(subjectData.getOneSubjectName());
            eduSubjectService.save(eduSubject);
        }
        existEduSubject = existEduSubject(eduSubjectService, subjectData.getOneSubjectName());
        //二级分类
        String pid=existEduSubject.getId();
        EduSubject existTwoEduSubject = existTwoEduSubject(eduSubjectService, subjectData.getTwoSubjectName(), pid);
        if (existTwoEduSubject == null) {  //没有相同的一级分类
            EduSubject eduSubject = new EduSubject();
            eduSubject.setParentId(pid);
            eduSubject.setTitle(subjectData.getTwoSubjectName());
            eduSubjectService.save(eduSubject);
        }
    }

    //判断一级分类不能重复添加
    public EduSubject existEduSubject(EduSubjectService eduSubjectService,String name){
        QueryWrapper<EduSubject> queryWrap=new QueryWrapper<>();
        queryWrap.eq("title",name);
        queryWrap.eq("parent_id","0");
        EduSubject oneSubject = eduSubjectService.getOne(queryWrap);
        return oneSubject;
    }
    //判断二级分类不能重复添加
    public EduSubject existTwoEduSubject(EduSubjectService eduSubjectService,String name,String pid){
        QueryWrapper<EduSubject> queryWrap=new QueryWrapper<>();
        queryWrap.eq("title",name);
        queryWrap.eq("parent_id",pid);
        EduSubject oneSubject = eduSubjectService.getOne(queryWrap);
        return oneSubject;
    }
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
