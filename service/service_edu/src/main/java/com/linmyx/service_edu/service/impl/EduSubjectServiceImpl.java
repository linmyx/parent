package com.linmyx.service_edu.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.linmyx.commonUtils.Result;
import com.linmyx.service_edu.entity.EduSubject;
import com.linmyx.service_edu.entity.subject.OneSubject;
import com.linmyx.service_edu.entity.subject.TwoSubject;
import com.linmyx.service_edu.excel.SubjectData;
import com.linmyx.service_edu.listener.SubjectExcelListener;
import com.linmyx.service_edu.mapper.EduSubjectMapper;
import com.linmyx.service_edu.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-11-07
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    //添加课程分类
    @Override
    public void saveSubject(MultipartFile file,EduSubjectService eduSubjectService) {
        try {
            //文件的输入流
            InputStream inputStream = file.getInputStream();
            EasyExcel.read(inputStream, SubjectData.class,new SubjectExcelListener(eduSubjectService)).sheet().doRead();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 课程分类列表树形结构
     * @return
     */
    @Override
    public Result getAllSubject() {
        //查询所有的一级分类
        QueryWrapper<EduSubject> queryWrapOne = new QueryWrapper<>();
        queryWrapOne.eq("parent_id","0");
        List<EduSubject> listOne = list(queryWrapOne);
        //查询所有的二级分类
        QueryWrapper<EduSubject> queryWrapTwo = new QueryWrapper<>();
        queryWrapTwo.ne("parent_id",0);
        List<EduSubject> listTwo = list(queryWrapTwo);
        //封装
        List<OneSubject> finalSubjectList=new ArrayList<>();
        for (int i =0;i<listOne.size(); i++){
            EduSubject eduSubject = listOne.get(i);
            OneSubject oneSubject = new OneSubject();
            //对象复制
            BeanUtils.copyProperties(eduSubject,oneSubject);
            List<TwoSubject> twoFIneSubjectList=new ArrayList<>();
            for (int j = 0; j < listTwo.size(); j++) {
                //获取每个二级分类
                EduSubject twoEduSubject = listTwo.get(j);
                if (eduSubject.getId().equals(twoEduSubject.getParentId())){
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(twoEduSubject, twoSubject);
                    twoFIneSubjectList.add(twoSubject);
                }
                oneSubject.setChildren(twoFIneSubjectList);
            }
            finalSubjectList.add(oneSubject);
        }
        return Result.ok(finalSubjectList);
    }
}
