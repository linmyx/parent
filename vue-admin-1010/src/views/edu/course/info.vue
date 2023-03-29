<template>

    <div class="app-container">
  
      <h2 style="text-align: center;">发布新课程</h2>
  
      <el-steps :active="1" process-status="wait" align-center style="margin-bottom: 40px;">
        <el-step title="填写课程基本信息"/>
        <el-step title="创建课程大纲"/>
        <el-step title="最终发布"/>
      </el-steps>
        <el-form label-width="120px">
        <el-form-item label="课程标题">
        <el-input v-model="courseInfo.title" placeholder=" 示例：机器学习项目课：从基础到搭建项目视频课程。专业名称注意大小写"/>
        </el-form-item>

        <!-- 所属分类 TODO -->

        <!-- 课程讲师 TODO -->
        <el-form-item label="课程分类">
        <el-select
            v-model="courseInfo.subjectParentId"
            placeholder="一级分类" @change="subjectLevelOneChanged">
            <el-option
            v-for="subjectOne in subjectOneList"
            :key="subjectOne.id"
            :label="subjectOne.title"
            :value="subjectOne.id"/>
        </el-select>
        <!-- 二级分类 -->
        <el-select v-model="courseInfo.subjectId" placeholder="二级分类">
        <el-option
            v-for="subject in subjectTwoList"
            :key="subject.id"
            :label="subject.title"
            :value="subject.id"/>
        </el-select>
        </el-form-item>
        <!-- 课程讲师 -->
        <el-form-item label="课程讲师">
        <el-select
            v-model="courseInfo.teacherId"
            placeholder="请选择">
            <el-option
            v-for="teacher in teacherList"
            :key="teacher.id"
            :label="teacher.name"
            :value="teacher.id"/>
        </el-select>
        </el-form-item>
        <el-form-item label="总课时">
        <el-input-number :min="0" v-model="courseInfo.lessonNum" controls-position="right" placeholder="请填写课程的总课时数"/>
        </el-form-item>

        <!-- 课程简介 TODO -->
        <el-form-item label="课程简介">
            <tinymce :height="300" v-model="courseInfo.description"/>
        </el-form-item>
        <!-- 课程封面 TODO -->
        <!-- 课程封面-->
            <el-form-item label="课程封面">
            <el-upload
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload"
            :action="BASE_API+'/eduoss/fileoss'"
            class="avatar-uploader">
            <img :src="courseInfo.cover">
            </el-upload>
           </el-form-item>
        <el-form-item label="课程价格">
        <el-input-number :min="0" v-model="courseInfo.price" controls-position="right" placeholder="免费课程请设置为0元"/> 元
        </el-form-item>
        <el-form-item>
          <el-button :disabled="saveBtnDisabled" type="primary" @click="saveOrUpdata">保存并下一步</el-button>
        </el-form-item>
      </el-form>
    </div>
  </template>
  <script>
  import course from '@/api/course'
  import subject from '@/api/subject'
  import Tinymce from '@/components/Tinymce'
  export default {
    components: { Tinymce },
    data() {
      return {
        courseInfo:{
            title: '',
            subjectParentId: '',  //一级分类
            subjectId: '',   //二级分类
            teacherId: '',
            lessonNum: 0,
            description: '',
            cover: '/static/01.jpg',
            price: 0
        },
        saveBtnDisabled: false, // 保存按钮是否禁用
        teacherList:[],
        subjectOneList:[],
        subjectTwoList:[],
        BASE_API: process.env.BASE_API, // 接口API地址
        courceId:""
      }
    },
  
    created() {
        if (this.$route.params && this.$route.params.id) {
                 this.courceId = this.$route.params.id
                 this.getCourseById()

        } else {
            this.getListTeacher()
            this.getOneSubject()
            this.courseInfo={
                title: '',
                subjectParentId: '',  //一级分类
                subjectId: '',   //二级分类
                teacherId: '', 
                lessonNum: 0,
                description: '',
                cover: '/static/01.jpg',
                price: 0
            }
        }
        
    },
  
    methods: {
        //数据回显
        getCourseById(){
            course.getCourseById(this.courceId)
            .then(response =>{
                this.courseInfo=response.data
                //先查询出所有的分类
                subject.getSubject()
                .then(response =>{
                    //获取所有的一级分类
                    this.subjectOneList=response.data
                    //3.把所有的一级分类数组进行对比
                    for(let i =0;i<this.subjectOneList.length;i++){
                        var oneSubject=this.subjectOneList[i];
                        if(this.courseInfo.subjectParentId == oneSubject.id){
                            //获取一级分类中的二级分类
                            this.subjectTwoList=oneSubject.children
                        }
                    }
                })
                this.getListTeacher()
            })
        },
       
        //上传成功
        handleAvatarSuccess(res,file){
            this.courseInfo.cover = res.data
        },
        //上传之前
        beforeAvatarUpload(file){
            const isJPG = file.type === 'image/jpeg'
            const isLt2M = file.size / 1024 / 1024 < 2
            if (!isJPG) {
                this.$message.error('上传头像图片只能是 JPG 格式!')
            }
            if (!isLt2M) {
                this.$message.error('上传头像图片大小不能超过 2MB!')
            }
            return isJPG && isLt2M
        },
        //绑定二级分类
        subjectLevelOneChanged(value){
            //value就是一级分类的id值
            for (let i = 0; i < this.subjectOneList.length; i++) {
                if (this.subjectOneList[i].id === value) {
                    this.subjectTwoList = this.subjectOneList[i].children
                    this.courseInfo.subjectId = ''
                }
            }
        },
        getOneSubject(){
            subject.getSubject()
            .then(response=>{
                this.subjectOneList=response.data
            })
        },
        saveOrUpdata() {
            if(this.courseInfo.id){
                this.updateCourse()
            }else {
                this.saveCourse()
            }
        },
        saveCourse(){
            course.addCourseInfo(this.courseInfo)
                  .then(response =>{
                if(response.success){
                    this.$message({
                    type: 'success',
                    message: "添加课程信息成功"
                  })
                  this.$router.push({ path: '/course/chapter/'+ response.data})
                }else {
                    this.$message({
                    type: 'success',
                    message: "添加课程信息失败"
                  })
                }
            })
        },
         //修改课程
         updateCourse(){
            course.updateCourse(this.courseInfo)
            .then(response => {
                if(response.success){
                    this.$message({
                    type: 'success',
                    message: "修改课程信息成功"
                  })
                  this.$router.push({ path: '/course/chapter/'+ this.courceId})
                } else {
                    this.$message({
                    type: 'success',
                    message: "修改课程信息失败"
                  })
                }
            })
        },
        getListTeacher(){
            course.getListTeacher()
            .then(response => {
                this.teacherList=response.data
            })
        }
    }
  }
  </script>

<style scoped>
.tinymce-container {
  line-height: 29px;

}
</style>